<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white mt-4">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
        <el-form-item label="车辆品牌" prop="carBrandId">
          <el-select v-model="queryParams.carBrandId" placeholder="请选择" clearable filterable style="width: 240px">
            <el-option v-for="dict in carBrand" :key="dict.keyId" :label="dict.carBrandName" :value="dict.keyId" />
          </el-select>
        </el-form-item>
        <el-form-item label="车辆类型" prop="carTypeId">
          <el-select v-model="queryParams.carTypeId" placeholder="请选择" clearable style="width: 240px">
            <el-option v-for="dict in carType" :key="dict.keyId" :label="dict.carTypeName" :value="dict.keyId" />
          </el-select>
        </el-form-item>
        <el-form-item label="车辆型号" prop="carModelId">
          <el-select v-model="queryParams.carModelId" placeholder="请选择" clearable style="width: 240px">
            <template v-for="dict in carMode" :key="dict.keyId">
              <el-option :label="dict.carModelName" :value="dict.keyId" />
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="所属企业" prop="companyId">
          <el-select v-model="queryParams.companyId" placeholder="请选择" clearable style="width: 240px">
            <template v-for="dict in epList" :key="dict.keyId">
              <el-option :label="dict.epName" :value="dict.keyId" />
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="车牌号" prop="license">
          <el-input v-model="queryParams.license" style="width: 240px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>平台车辆列表</div>
        <div class="flex">
          <el-button type="primary" icon="Download" @click="handleExport">导出</el-button>
        </div>
      </div>
      <div class="mt-4">
        <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
          <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
            <template #default="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="车牌号" align="center" prop="license" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="车辆型号" align="center" prop="carModelName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="车辆类型" align="center" prop="carTypeName" :show-overflow-tooltip="true" min-width="150">
          </el-table-column>
          <el-table-column label="车辆品牌" align="center" prop="carBrandName" :show-overflow-tooltip="true"
            min-width="150" />
            <el-table-column label="能源类型" align="center" prop="energyTypeName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="注册日期" align="center" prop="registerDate" :show-overflow-tooltip="true" min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.registerDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="添加时间" align="center" prop="insertTimestamp" :show-overflow-tooltip="true" min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.insertTimestamp) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="所属企业" align="center" prop="companyName" :show-overflow-tooltip="true"
            min-width="150" />
        </el-table>
        <pagination class="mt-4" v-show="total > 0" :total="total" v-model:page="queryParams.current"
          v-model:limit="queryParams.size" @pagination="getList" />
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  fixCarList,
  carBrandList,
  carModelList,
  carTypeList, 
  fixedEpList
} from "@/api/car";

const { proxy } = getCurrentInstance();

// 获取列表
const queryParams = reactive({
  current: 1, 
  size: 10,
  carBrandId: undefined,
  carModelId: undefined,
  companyId: undefined,
  carTypeId: undefined,
  license: undefined
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
const carBrand = ref([]);
const carMode = ref([]);
const carType = ref([]);
const epList = ref([])
getList();
initData();
function initData() {
  carTypeList({exportType: 1 })
    .then((res) => {
      carType.value = res.records;
    })
    .finally(() => { });
  carBrandList({exportType: 1 })
    .then((res) => {
      carBrand.value = res.records;
    })
    .finally(() => { });
  carModelList({exportType: 1 })
    .then((res) => {
      carMode.value = res.records;
    })
    .finally(() => { });
    fixedEpList({epType: 2 })
    .then((res) => {
      epList.value = res.records;
    })
    .finally(() => { });
}
function getList() {
  loading.value = true;
  fixCarList(queryParams)
    .then((res) => {
      list.value = res.records;
      total.value = +res.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

const inVisible = ref(false);

const rowData = ref({})

// 查看
const visible = ref(false);

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

// 导出
function handleExport() {
  proxy.download(
    "/zoom-fleet-manager/car/exportPlatformCarList",
    {
      ...queryParams,
      exportType: 1,
    },
    "平台车辆列表.xls"
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
