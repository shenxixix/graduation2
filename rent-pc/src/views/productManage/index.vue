<template>
  <div class="min-h-full flex flex-col p-4 bg-gray-50">
    <div class="p-4 pb-0 bg-white">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
        <el-form-item label="车辆类型" prop="carTypeId">
          <el-select v-model="queryParams.carTypeId" placeholder="请选择" style="width: 240px">
            <el-option v-for="dict in carType" :key="dict.keyId" :label="dict.carTypeName" :value="dict.keyId" />
          </el-select>
        </el-form-item>
        <el-form-item label="上架状态" prop="upStatus">
          <el-select v-model="queryParams.upStatus" placeholder="请选择" style="width: 240px">
            <el-option label="已上架" :value="1" />
            <el-option label="已下架" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="车辆型号" prop="carModelName">
          <el-input v-model="queryParams.carModelName" placeholder="请输入" clearable style="width: 240px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="bg-white flex-1 p-4 mt-4">
      <div class="flex justify-between items-center">
        <div>产品列表</div>
        <div>
          <el-button type="primary" icon="Plus" @click="doAdd">添加</el-button>
          <el-button type="primary" @click="handleExport">导出</el-button>
        </div>
      </div>
      <div class="mt-4">
        <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
          <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
            <template #default="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="车辆型号" align="center" prop="carModelName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="车辆品牌" align="center" prop="carBrandName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="车辆类型" align="center" prop="carTypeName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="日租费区间(元/天)" align="center" prop="dayFeeRange" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="产品特色" align="center" prop="configParamsInfo" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="上架状态" align="center" prop="upStatus" :show-overflow-tooltip="true" min-width="150" >
            <template #default="scope">
              <span>{{ scope.row.upStatus==1?'已上架':'已下架' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="车辆总数" align="center" prop="carTotalNum" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="报价中" align="center" prop="quotationNum" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="租赁中" align="center" prop="rentedNum" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="维修中" align="center" prop="faultNum" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="可租赁" align="center" prop="rentNum" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="操作人" align="center" prop="optUserName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="操作时间" align="center" :show-overflow-tooltip="true" min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.updateTimestamp) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150" fixed="right">
            <template #default="scope">
              <div class="flex items-center justify-center">
                <el-button type="primary" text @click="doAdd(scope.row)">编辑</el-button>
                <el-dropdown @command="handleCommand">
                  <span class="el-dropdown-link">
                    更多
                    <el-icon class="el-icon--right">
                      <arrow-down />
                    </el-icon>
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item :command="{ id: scope.row.keyId,type: 'change',status:scope.row.upStatus }">
                        <span :class="scope.row.upStatus == 2 ? 'cl3' : 'cl9'">下架</span>/
                        <span :class="scope.row.upStatus == 1 ? 'cl3' : 'cl9'">上架</span>
                      </el-dropdown-item>
                      <el-dropdown-item :command="{ id: scope.row.keyId,type: 'del' }">删除</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="mt-2 tips">
          <div>说明</div>
          <div class="mt-2">1、车辆总数：车辆档案中与所选车辆型号相同的车辆数</div>
          <div>2、报价中：当前报了价的车辆，但还未确定租赁</div>
          <div>3、租赁中：当前正处在租赁中的车辆</div>
          <div>4、维修中：当前被标记为维修的车辆</div>
          <div>5、可租赁：车辆总数-报价中-租赁中-维修中</div>
        </div>
        <pagination class="mt-4" v-show="total > 0" :total="total" v-model:page="queryParams.current"
          v-model:limit="queryParams.size" @pagination="getList" />
      </div>
    </div>
    <Add v-model="visible" v-if="visible" :keyId="keyId" @update="resetQuery" />
  </div>
</template>

<script setup>
import Add from './add.vue';
import { fixProductList, updateProductStatus, deleteFixProduct } from "@/api/product";
import { carTypeList } from "@/api/car";

const { proxy } = getCurrentInstance();

// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
  upStatus: null,
  carModelName: '',
  carTypeId: null
});
const loading = ref(false);
const list = ref([]);
const carType = ref([]);
const total = ref(0);
getList();
getData()
function getList() {
  loading.value = true;
  fixProductList(queryParams)
    .then((res) => {
      list.value = res.records;
      total.value = +res.total;
    })
    .finally(() => {
      loading.value = false;
    });
}
function getData() {
  carTypeList({})
    .then((res) => {
      carType.value = res.records
    })
    .finally(() => {
    });
}

function handleCommand(val) {
  console.log(val, 123)
  if(val.type == 'del'){
    ElMessageBox.confirm("确定删除吗", "提示", {
    confirmButtonText: "删除",
    cancelButtonText: "取消",
    showClose: false,
  })
    .then(() => {
      let params = {
        keyId: val.id,
      }
      deleteFixProduct(params)
        .then((res) => {
          resetQuery()
        })
        .finally(() => {
        });
    })
    .catch(() => {});
  }else {
    let params = {
        keyId: val.id,
        upStatus: val.status==1?2:1
      }
      updateProductStatus(params)
        .then((res) => {
          resetQuery()
        })
        .finally(() => {
        });
  }
}

// 添加
const visible = ref(false);
const keyId = ref("");
function doAdd(row) {
  if (row.keyId) {
    keyId.value = row.keyId;
  } else {
    keyId.value = "";
  }
  visible.value = true;
}

// 删除
function doDel(id) {
  ElMessageBox.confirm("确定要删除吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      deleteFixProduct(id).then(() => {
        proxy.$message.success("操作成功");
        getList();
      });
    })
    .catch(() => { });
}

// 重置
function resetQuery() {
  queryParams.size = 10;
  queryParams.current = 1; // 页码还原
  proxy.$refs.queryRef.resetFields(); // 表单还原
  getList(); // 更新列表
}

// 搜索
function handleQuery() {
  queryParams.current = 1; // 查询第一页
  getList(); // 更新列表
}

function handleExport() {
  proxy.download(
    "/zoom-fleet-manager/product/exportFixProductList",
    {
      ...queryParams,
      exportType: 1,
    },
    "产品列表.xls"
  );
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
  background: $theme;
  color: white;
}

.tips {
  color: #666666;
  font-size: 12px;
}

.cl3 {
  color: #333333;
}

.cl3 {
  color: #999999;
}
</style>
