<template>
    <div class="p-4 bg-gray-50 min-h-full flex flex-col">
        <div class="p-4 pb-0 bg-white">
            <el-form :model="queryParams" ref="queryRef" :inline="true">
                <el-form-item label="所属平台" prop="enterpriseId">
                    <el-select
                        v-model="queryParams.enterpriseId"
                        placeholder="请选择"
                        clearable
                        style="width: 240px"
                    >
                        <el-option
                            v-for="dict in enterpriseList"
                            :key="dict.keyId"
                            :label="dict.enterpriseName"
                            :value="dict.keyId"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="商家名称" prop="stationName">
                    <el-input v-model="queryParams.stationName" placeholder="请输入" clearable style="width: 240px" />
                </el-form-item>
                <el-form-item label="状态" prop="delFlag">
                    <el-select
                        v-model="queryParams.delFlag"
                        placeholder="请选择"
                        clearable
                        style="width: 240px"
                    >
                        <el-option
                            v-for="dict in stationStatus"
                            :key="dict.value"
                            :label="dict.label"
                            :value="dict.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                    <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="p-4 bg-white mt-4 flex-1">
            <div class="flex justify-between items-center">
                <div>{{ listName }}</div>
                <el-button type="primary" icon="Download" @click="handleExport">导出</el-button>
            </div>
            <div class="mt-4">
                <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
                    <el-table-column label="序号" align="center" width="100" fixed="left">
                        <template #default="scope">
                            <span>{{ scope.$index + 1 }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="商家名称" align="center" prop="stationName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="商家号" align="center" prop="stationCode" :show-overflow-tooltip="true" min-width="200" />
                    <el-table-column label="联系电话" align="center" prop="phoneNum" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="门店地址" align="center" prop="location" :show-overflow-tooltip="true" min-width="200" />
                    <el-table-column label="所属平台" align="center" prop="enterpriseName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="商家类型" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag :options="enterpriseType" :value="scope.row.enterpriseType" />
                        </template>
                    </el-table-column>
                    <el-table-column label="管理员" align="center" prop="adminUserName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="管理员电话" align="center" prop="adminMobile" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="审核通过时间" align="center" :show-overflow-tooltip="true" min-width="200">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.agreeTimestamp) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="状态" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag :options="stationStatus" :value="scope.row.delFlag" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作人" align="center" prop="handleUser" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="操作人属性" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag :options="source" :value="scope.row.handleSource" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作时间" align="center" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.optTimestamp) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="150" fixed="right">
                        <template #default="scope">
                            <div class="flex items-center justify-center">
                                <el-button type="success" text @click="doView(scope.row.stationId)">查看</el-button>
                                <el-button type="primary" text @click="doDel(scope.row.stationId)" v-if="!scope.row.delFlag">删除</el-button>
                            </div>
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
        <Detail v-model="visible" v-if="visible" :keyId="keyId" />
    </div>
</template>

<script setup>
import Detail from './detail.vue';
import { queryEnterpriseStationPage, queryEnterpriseList, deleteStation } from '@/api/business';

const { proxy } = getCurrentInstance();

// 字典
const { stationStatus, enterpriseType, source } = proxy.dict(['stationStatus', 'enterpriseType', 'source']);

const props = defineProps({
    type: {
        type: [String, Number],
        default: ''
    },
});

const listName = computed(() => {
    const arr = ['配送站点列表', '租车商户列表', '合作服务商列表'];
    return arr[props.type - 1];
});

// 获取列表
const queryParams = reactive({
    current: 1,
    size: 10,
    enterpriseId: undefined,
    stationName: undefined,
    delFlag: undefined,
});

const loading = ref(false);
const list = ref([]);
const total = ref(0);

watch(() => props.type, val => {
    queryParams.stationType = val;
    resetQuery();
    getEnterpriseList();
}, { immediate: true });

getList();
function getList() {
    loading.value = true;
    queryEnterpriseStationPage(queryParams).then(res => {
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
    queryEnterpriseList({ enterpriseType: queryParams.stationType }).then(res => {
        enterpriseList.value = res.data
    })
}

// 查看
const visible = ref(false);
const keyId = ref('');
function doView(id) {
    keyId.value = id;
    visible.value = true;
}

// 删除
function doDel(id) {
    ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        deleteStation(id).then(() => {
            proxy.$message.success('操作成功');
            getList();
        })
    }).catch(() => { });
}

// 重置
function resetQuery() {
    queryParams.size = 10;
    queryParams.current = 1; // 页码还原
    if (proxy.$refs.queryRef) {
        proxy.$refs.queryRef.resetFields(); // 表单还原
    }
    getList(); // 更新列表
}

// 搜索
function handleQuery() {
    queryParams.current = 1; // 查询第一页
    getList(); // 更新列表
}

// 导出
function handleExport() {
    proxy.download("/hw-opt-server/enterprise/exportEnterpriseStationList", {
        ...queryParams,
    }, '配送站点列表.xls');
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}
</style>
