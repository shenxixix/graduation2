<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
        <el-form-item label="车辆型号" prop="carModelId">
          <el-select v-model="queryParams.carModelId" placeholder="请选择" clearable style="width: 240px">
            <template v-for="dict in carMode" :key="dict.keyId">
              <el-option :label="dict.carModelName" :value="dict.keyId" />
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="驾驶方式" prop="driveType">
          <el-select v-model="queryParams.driveType" placeholder="请选择" clearable style="width: 240px">
            <el-option label="配司机" value="2" />
            <el-option label="自驾" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="用车人" prop="useUserName">
          <el-input v-model="queryParams.useUserName" style="width: 240px" />
        </el-form-item>
        <el-form-item label="用车单位" prop="useOrgName">
          <el-input v-model="queryParams.useOrgName" style="width: 240px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>已报价列表</div>
        <el-button type="primary" @click="handleExport">导出</el-button>
        <!-- <el-button type="primary" icon="Plus" @click="doView">报价详情</el-button> -->
      </div>
      <div class="mt-4">
        <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
          <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
            <template #default="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="租赁报价" align="center" prop="rentQuotedFeeInfo" width="150">
            <template #default="scope">
              <el-button type="text" @click="doView(scope.row.keyId)" class="change">{{ scope.row.rentQuotedFeeInfo }}</el-button>
            </template>
          </el-table-column>
          <el-table-column label="所需车数" align="center" prop="carNum" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="所需车型" align="center" prop="carModelInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="联系人" align="center" prop="userNickName" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="联系人手机" align="center" prop="userMobile" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="用车单位" align="center" prop="orgName" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="驾驶方式" align="center" prop="driveType" :show-overflow-tooltip="true"
            min-width="150">
            <template #default="scope">
              <span>{{ scope.row.driveType == 1?'自驾':'配司机' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="用车时间" align="center" prop="pickUpCarTime" :show-overflow-tooltip="true"
            min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.pickUpCarTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="还车时间" align="center" prop="backCarTime" :show-overflow-tooltip="true"
            min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.backCarTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="取车点" align="center" prop="startAddress" :show-overflow-tooltip="true" min-width="150">
          </el-table-column>
          <el-table-column label="还车点" align="center" prop="endAddress" :show-overflow-tooltip="true" min-width="150">
          </el-table-column>
          <el-table-column label="提交时间" align="center" prop="insertTimestamp" :show-overflow-tooltip="true"
            min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.insertTimestamp) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作人" align="center" prop="operateUserName" :show-overflow-tooltip="true" min-width="150">
          </el-table-column>
          <el-table-column label="操作时间" align="center" prop="operateTime" :show-overflow-tooltip="true"
            min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.operateTime) }}</span>
            </template>
          </el-table-column>
        </el-table>
        <pagination class="mt-4" v-show="total > 0" :total="total" v-model:page="queryParams.current"
          v-model:limit="queryParams.size" @pagination="getList" />
      </div>
    </div>
    <Add v-model="dialogVisible" v-if="dialogVisible" :keyId="keyId" @update="handleUpdate"></Add>
    <QuoteDetail v-model="quoteVisible" v-if="quoteVisible" :id="keyId"></QuoteDetail>
  </div>
</template>

<script setup>
import { quotedList } from "@/api/deal";
import { carModelList } from "@/api/car"
import Add from "./add.vue";
const { proxy } = getCurrentInstance();


// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
  useOrgName: undefined,
  useUserName: undefined,
  driveType: undefined,
  carModelId: undefined
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
const carMode = ref([])
const dialogVisible = ref(false)
getList();
initData();
function initData() {
  carModelList({}).then((res) => { carMode.value = res.records }).finally(() => { })
}
function getList() {
  loading.value = true;
  quotedList(queryParams)
    .then((res) => {
      list.value = res.records;
      total.value = +res.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

// 查看
const visible = ref(false);
const quoteVisible = ref(false)
const keyId = ref("");
function doView(id) {
  keyId.value = id;
  quoteVisible.value = true;
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
function handleUpdate() {
  getList()
}

// 导出
function handleExport() {
  proxy.download(
    "/zoom-fleet-manager/quotationTodo/exportQuotedList",
    {
      ...queryParams,
      exportType: 1,
    },
    "已报价列表.xls"
  );
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
  background: $theme;
  color: white;
}

.change {
  color: rgb(0, 128, 255);
}
</style>
