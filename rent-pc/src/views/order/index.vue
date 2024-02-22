<template>
    <div class="min-h-full flex flex-col" :class="!isHome ? 'p-4 bg-gray-50' : ''">
        <div class="p-4 pb-0 bg-white" v-if="!isHome">
            <el-form :model="queryParams" ref="queryRef" :inline="true">
                <el-form-item label="订单状态" prop="orderStatus">
                    <el-select
                        v-model="queryParams.orderStatus"
                        placeholder="请选择"
                        clearable
                        style="width: 240px"
                    >
                        <el-option
                            v-for="dict in dict('orderStatus')"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="取车状态" prop="getStatus">
                    <el-select
                        v-model="queryParams.getStatus"
                        placeholder="请选择"
                        clearable
                        style="width: 240px"
                    >
                        <el-option
                            v-for="dict in dict('pickStatus')"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="承租人" prop="userNickName">
                    <el-input v-model="queryParams.userNickName" placeholder="请输入" clearable style="width: 240px" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                    <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="bg-white flex-1" :class="!isHome ? 'p-4 mt-4' : ''">
            <div class="flex justify-between items-center" v-if="!isHome">
                <div>租赁订单列表</div>
                <el-button type="primary" icon="Download" @click="handleExport">导出</el-button>
            </div>
            <div :class="!isHome ? 'mt-4' : ''">
                <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
                    <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
                        <template #default="scope">
                            <span>{{ scope.$index + 1 }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="订单编号" align="center" prop="orderCode" :show-overflow-tooltip="true" min-width="200">
                        <template #default="scope">
                            <span class="cursor-pointer text-blue-700 hover:opacity-80" @click="doView(scope.row.orderId)">{{ scope.row.orderCode }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="承租人" align="center" prop="userNickName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="承租人电话" align="center" prop="userMobile" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="租赁期限" align="center" prop="enterpriseName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="合约时间" align="center" prop="rentTime" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.rentTime) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="订单状态" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag name="orderStatus" :value="scope.row.orderStatus" />
                        </template>
                    </el-table-column>
                    <el-table-column label="租赁车型" align="center" prop="carModelName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="取车状态" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag name="pickStatus" :value="scope.row.getStatus" />
                        </template>
                    </el-table-column>
                    <el-table-column label="取车时间" align="center" prop="getTime" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.getTime) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="车辆ID" align="center" prop="carId" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="租金情况" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag name="rentStatus" :value="scope.row.rentStatus" />
                        </template>
                    </el-table-column>
                </el-table>
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
        <Detail v-model="visible" v-if="visible" :keyId="keyId"></Detail>
    </div>
</template>

<script setup>
import Detail from './detail.vue';
import { queryEnterpriseList, queryEnterpriseStationPage } from '@/api/business';
import { queryRentOrderPage } from '@/api/order';

const { proxy } = getCurrentInstance();
const props = defineProps({
    isHome: {
        type: Boolean,
        default: false
    },
});

// 字典
const { enterpriseType, pickStatus, orderStatus } = proxy.dict(['enterpriseType', 'pickStatus', 'orderStatus']);

// 获取列表
const queryParams = reactive({
    current: 1,
    size: 10,
    rentObject: undefined,
    userNickName: undefined,
    enterpriseType: undefined,
    enterpriseId: undefined,
    stationId: undefined,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
getList();
function getList() {
    loading.value = true;
    queryRentOrderPage(queryParams).then(res => {
        list.value = res.data.records;
        total.value = +res.data.total;
    }).finally(() => {
        loading.value = false;
    });
}

// 获取企业下拉列表
const enterpriseList = ref([]);
getEnterpriseList();
function getEnterpriseList() {
    const p = {
        status: 1,
        enterpriseType: queryParams.enterpriseType
    };
    queryEnterpriseList(p).then(res => {
        enterpriseList.value = res.data
    })
}

// 选择商家类型
function enterpriseTypeChange() {
    queryParams.enterpriseId = '';
    enterpriseList.value = [];
    getEnterpriseList();
    enterpriseIdChange();
}

// 选择平台名称
function enterpriseIdChange() {
    queryParams.stationId = '';
    stationList.value = [];
    getStationList();
}

// 获取配送站点下拉列表
const stationList = ref([]);
getStationList();
function getStationList() {
    const p = {
        exportType: 1,
        enterpriseId: queryParams.enterpriseId
    };
    queryEnterpriseStationPage(p).then(res => {
        stationList.value = res.data.records
    })
}

// 去处理
const visible = ref(false);
const keyId = ref('');
function doView(id) {
    keyId.value = id;
    visible.value = true;
}

// 重置
function resetQuery() {
    queryParams.size = 10;
    queryParams.current = 1; // 页码还原
    proxy.$refs.queryRef.resetFields(); // 表单还原
    enterpriseTypeChange();
    getList(); // 更新列表
}

// 搜索
function handleQuery() {
    queryParams.current = 1; // 查询第一页
    getList(); // 更新列表
}

// 导出
function handleExport() {
    proxy.download("/hw-opt-server/rent/exportRentOrder", {
        ...queryParams,
    }, '租赁订单列表.xls');
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}
</style>
