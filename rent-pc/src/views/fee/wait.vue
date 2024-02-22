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
        <el-form-item label="派遣车辆" prop="license">
          <el-input v-model="queryParams.license" style="width: 240px" />
        </el-form-item>
        <el-form-item label="派遣驾驶员" prop="driverName">
          <el-input v-model="queryParams.driverName" style="width: 240px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>待结算列表</div>
        <div>
          <el-button type="primary" @click="handleSettleAll" :disabled="selData.length==0">批量结算</el-button>
          <el-button type="primary" @click="handleExport">导出</el-button>
        </div>
      </div>
      <div class="mt-4">
        <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" />
          <el-table-column label="总租赁费(元)" align="center" prop="rentFeeInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="租车费(元)" align="center" prop="carPriceInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="驾驶员费用(元)" align="center" prop="driverPriceInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="超里程费(元)" align="center" prop="overMileFee" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="超时长费(元)" align="center" prop="overDurationFee" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="过路费(元)" align="center" prop="roadFeeInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="停车费(元)" align="center" prop="stopFeeInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="住宿费(元)" align="center" prop="stayFeeInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="日均价(元/天)" align="center" prop="dayAvgFeeInfo" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="租赁天数" align="center" prop="rentDayNum" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="实际里程(公里)" align="center" prop="appDriveMile" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="实际时长(天)" align="center" prop="appDriveDuration" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="已派车辆" align="center" prop="license" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="驾驶员" align="center" prop="driverName" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="送取车人" align="center" prop="obtainName" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="用车人" align="center" prop="useUserName" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="用车人手机" align="center" prop="useUserMobile" :show-overflow-tooltip="true"
            min-width="150" />
          <el-table-column label="用车单位" align="center" prop="useUserOrgName" :show-overflow-tooltip="true"
            min-width="150">
          </el-table-column>
          <el-table-column label="驾驶方式" align="center" prop="driveType" :show-overflow-tooltip="true" min-width="150">
            <template #default="scope">
              <span>{{ scope.row.driveType == 1 ? '自驾' : '配司机' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="车辆型号" align="center" prop="carModelName" :show-overflow-tooltip="true"
            min-width="150">
          </el-table-column>
          <el-table-column label="车辆品牌" align="center" prop="carBrandName" :show-overflow-tooltip="true"
            min-width="150">
          </el-table-column>
          <el-table-column label="车辆类型" align="center" prop="carTypeName" :show-overflow-tooltip="true"
            min-width="150">
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
          <el-table-column label="报价时间" align="center" prop="quotedTime" :show-overflow-tooltip="true"
            min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.quotedTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150" fixed="right">
            <template #default="scope">
              <el-button type="text" @click="doView(scope.row.dispatchId)" class="change">费用明细</el-button>
              <el-button type="danger" text='danger' @click="handleSettle(scope.row)" >结算</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination class="mt-4" v-show="total > 0" :total="total" v-model:page="queryParams.current"
          v-model:limit="queryParams.size" @pagination="getList" />
      </div>
    </div>
    <Detail v-model="dialogVisible" v-if="dialogVisible" :id="keyId"></Detail>
    <Settle v-model="visible" v-if="visible" :data="rowData" @update="resetQuery"></Settle>
  </div>
</template>

<script setup>
import { carModelList } from "@/api/car";
import { needFeeSettlementList } from "@/api/fee"
import Detail from "./detail.vue";
import Settle from "./settle.vue"
const { proxy } = getCurrentInstance();
// 字典
const { enterpriseType, source, applyStatus } = proxy.dict(["enterpriseType", "source", "applyStatus"]);

// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
  driverName: undefined,
  license: undefined,
  driveType: undefined,
  carModelId: undefined,
  useOrgName: undefined,
  useUserName: undefined,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
const carMode = ref([])
const dialogVisible = ref(false)
const editVisible = ref(false)
getList();
initData();
function initData() {
  carModelList({}).then((res) => { carMode.value = res.records }).finally(() => { })
}
function getList() {
  loading.value = true;
  needFeeSettlementList(queryParams)
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
  console.log(id,989)
  keyId.value = id;
  dialogVisible.value = true;
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


const rowData = ref([])
function handleSettle(val) {
  rowData.value = []
  rowData.value.push(val)
  visible.value = true
}

const selData = ref([])
function handleSelectionChange(val) {
  selData.value = val
  console.log(val,13)
  rowData.value = val
}

function handleSettleAll() {
  visible.value = true
}

// 导出
function handleExport() {
  proxy.download(
    "/zoom-fleet-manager/settlement/exportNeedFeeSettlementList",
    {
      ...queryParams,
      exportType: 1,
    },
    "待结算费用结算单列表.xls"
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
