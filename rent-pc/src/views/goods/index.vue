<template>
    <div class="min-h-full flex flex-col p-4 bg-gray-50">
        <div class="p-4 pb-0 bg-white">
            <el-form :model="queryParams" ref="queryRef" :inline="true">
                <el-form-item label="产品名称" prop="goodsName">
                    <el-input v-model="queryParams.goodsName" placeholder="请输入" clearable style="width: 240px" />
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
                <el-button type="primary" icon="Plus" @click="doAdd">添加产品</el-button>
            </div>
            <div class="mt-4">
                <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
                    <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
                        <template #default="scope">
                            <span>{{ scope.$index + 1 }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="产品ID" align="center" prop="keyId" :show-overflow-tooltip="true" min-width="200" />
                    <el-table-column label="所属城市" align="center" prop="cityName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="产品名称" align="center" prop="goodsName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="车辆品牌" align="center" prop="carBrandName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="车辆型号" align="center" prop="carModelName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="押金(元)" align="center" prop="deposit" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="租金(元/月)" align="center" prop="rentMoney" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="租赁期限" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <span v-for="(item, index) in scope.row.tenancyList" :key="index">
                                <span v-if="item.tenancyType === 1">{{ item.tenancy }}个月</span>
                                <span v-if="item.tenancyType === 2">{{ item.tenancy }}年</span>
                                <span v-if="index < scope.row.tenancyList.length - 1">、</span>
                            </span>
                        </template>
                    </el-table-column>
                    <el-table-column label="创建人" align="center" prop="createUserName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="创建时间" align="center" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.gmtCreate) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="150" fixed="right">
                        <template #default="scope">
                            <div class="flex items-center justify-center">
                                <el-button type="success" text @click="doAdd(scope.row)">编辑</el-button>
                                <el-button type="primary" text @click="doDel(scope.row.keyId)">删除</el-button>
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
        <Add v-model="visible" v-if="visible" :keyId="keyId" @update="resetQuery" />
    </div>
</template>

<script setup>
import Add from './add.vue';
import { queryGoodsPage, deleteGoods } from '@/api/order';
import { queryEnterpriseList } from '@/api/business';

const { proxy } = getCurrentInstance();

// 获取列表
const queryParams = reactive({
    current: 1,
    size: 10,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
getList();
function getList() {
    loading.value = true;
    queryGoodsPage(queryParams).then(res => {
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
}

// 添加
const visible = ref(false);
const keyId = ref('');
function doAdd(row) {
    if (row.keyId) {
        keyId.value = row.keyId;
    } else {
        keyId.value = '';
    }
    visible.value = true;
}

// 删除
function doDel(id) {
    ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        deleteGoods(id).then(() => {
            proxy.$message.success('操作成功');
            getList();
        })
    }).catch(() => { });
}

// 重置
function resetQuery() {
    queryParams.size = 10;
    queryParams.current = 1; // 页码还原
    proxy.$refs.queryRef.resetFields(); // 表单还原
    getEnterpriseList();
    getList(); // 更新列表
}

// 搜索
function handleQuery() {
    queryParams.current = 1; // 查询第一页
    getList(); // 更新列表
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}
</style>
