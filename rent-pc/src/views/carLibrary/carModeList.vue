<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white">
      <el-form
        :model="queryParams"
        ref="queryRef"
        :inline="true"
      >
      <el-form-item label="车辆品牌" prop="carBrandId">
          <el-select
            v-model="queryParams.carBrandId"
            placeholder="请选择"
            clearable
            filterable 
          >
            <el-option
              v-for="dict in carBrand"
              :key="dict.keyId"
              :label="dict.carBrandName"
              :value="dict.keyId"
            />
          </el-select>
        </el-form-item>
         <el-form-item
              label="车辆类型"
              prop="carTypeId"
            >
              <el-select
                v-model="queryParams.carTypeId"
                placeholder="请选择"
              >
                <el-option
                  v-for="dict in carType"
                  :key="dict.keyId"
                  :label="dict.carTypeName"
                  :value="dict.keyId"
                />
              </el-select>
            </el-form-item>
            <el-form-item
              label="能源类型"
              prop="energy"
            >
              <el-select
                v-model="queryParams.energy"
                placeholder="请选择"
              >
                <el-option
                  v-for="dict in energys"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
        <el-form-item label="车辆型号" prop="carModelName">
          <el-input v-model="queryParams.carModelName"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>车辆型号列表</div>
        <el-button type="primary" icon="Plus" @click="handleAdd"
          >添加车型</el-button
        >
      </div>
      <div class="mt-4">
        <el-table
          v-loading="loading"
          :data="list"
          border
          :scrollbar-always-on="true"
          style="width: 100%"
        >
          <el-table-column
            label="序号"
            align="center"
            prop="num"
            width="100"
            fixed="left"
          >
            <template #default="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="车辆型号"
            align="center"
            prop="carModelName"
            :show-overflow-tooltip="true"
            min-width="180"
          >
          <template #default="scope">
              <span class="cursor-pointer text-blue-700 hover:opacity-80" @click="handleMode(scope.row)">{{ scope.row.carModelName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="所属品牌"
            align="center"
            prop="carBrandName"
            :show-overflow-tooltip="true"
            min-width="150"
          />
          <el-table-column
            label="车辆类型"
            align="center"
            prop="carTypeName"
            :show-overflow-tooltip="true"
            min-width="150"
          />
          <el-table-column
            label="能源类型"
            align="center"
            prop="energyTypeName"
            :show-overflow-tooltip="true"
            min-width="150"
          />
          <el-table-column
            label="操作人"
            align="center"
            prop="optUserName"
            :show-overflow-tooltip="true"
            min-width="150"
          >
          </el-table-column>
          <el-table-column
            label="操作时间"
            align="center"
            prop="updateTimestamp"
            :show-overflow-tooltip="true"
            min-width="180"
          >
            <template #default="scope">
              <span>{{ formatTime(scope.row.updateTimestamp) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="150"
            fixed="right"
          >
          <template #default="scope">
              <el-button type="text" @click="doView(scope.row.keyId)" class="change"
                >编辑</el-button
              >
              <el-button type="danger" text='danger' @click="handleDel(scope.row.keyId)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div class="mt-2 tips">注：在定点租赁管理平台车辆入库时，可导入的车辆型号名称必须与这里的匹配，否则不能导入成功</div>
        <pagination
          class="mt-4"
          v-show="total > 0"
          :total="total"
          v-model:page="queryParams.current"
          v-model:limit="queryParams.size"
          @pagination="getList"
        />
      </div>
    </div>
    <Add v-model="dialogVisible" v-if="dialogVisible" :keyId="keyId" @update="handleUpdate"></Add>
    <Edit v-model="editVisible" v-if="editVisible" :keyId="keyId" @update="handleUpdate"></Edit>
  </div>
</template>

<script setup>
import { carBrandList, carModelList, carTypeList, delCarModel } from "@/api/car";
import Add from "./add.vue";
import Edit from "./edit.vue";
const { proxy } = getCurrentInstance();
// 字典
const { energys } = proxy.dict(["energys"]);

// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
  carBrandId: undefined,
  carModelName: undefined,
  carTypeId: undefined,
  energy: undefined
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
const carBrand = ref([])
const carType = ref([])
const dialogVisible = ref(false)
const editVisible = ref(false)
const detailVisible = ref(false)
const rowData = ref({})
getList();
initData();
function initData() {
  carTypeList({}).then((res)=>{carType.value = res.records}).finally(()=>{})
  carBrandList({exportType: 1}).then((res)=>{carBrand.value = res.records}).finally(()=>{})
}
function getList() {
  loading.value = true;
  carModelList(queryParams)
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
const keyId = ref("");
function doView(id) {
  keyId.value = id;
  editVisible.value = true;
}
function handleMode(data){
  rowData.value = data
  keyId.value = data.keyId;
  detailVisible.value = true;
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

function handleDel(id) {
  ElMessageBox.confirm("确定删除车辆吗", "提示", {
    confirmButtonText: "删除",
    cancelButtonText: "取消",
    showClose: false,
  })
    .then(() => {
      let params = {
        keyId: id,
      }
      loading.value = true;
      delCarModel(params)
        .then((res) => {
          getList()
        })
        .finally(() => {
          loading.value = false;
        });
    })
    .catch(() => {});
}

// 导出
function handleAdd() {
  dialogVisible.value = true
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
  background: $theme;
  color: white;
}
.change{
  color: rgb(0, 128, 255);
}
.tips {
  color: #666666;
}
</style>
