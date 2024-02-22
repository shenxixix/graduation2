<template>
    <div class="p-4 bg-gray-50 min-h-full flex flex-col">
        <div class="p-4 bg-white flex-1">
            <div class="flex justify-between items-center">
                <div>角色列表</div>
                <el-button type="primary" icon="Plus" @click="doAdd">添加角色</el-button>
            </div>
            <div class="mt-4">
                <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
                    <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
                        <template #default="scope">
                            <span>{{ scope.$index + 1 }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="角色名" align="center" prop="roleName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="备注" align="center" prop="remarks" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="人员" align="center" prop="roleUserDesc" :show-overflow-tooltip="true" min-width="200" />
                    <!-- <el-table-column label="创建人" align="center" prop="creator" :show-overflow-tooltip="true" min-width="150" /> -->
                    <el-table-column label="创建时间" align="center" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.insertTimestamp) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" min-width="200" fixed="right">
                        <template #default="scope">
                            <div class="flex items-center justify-center">
                                <el-link type="primary" :underline="false" @click="doAdd(scope.row, true)">查看</el-link>
                                <el-link type="success" :underline="false" @click="doAdd(scope.row)">修改</el-link>
                                <el-link type="danger" :underline="false" v-if="+scope.row.roleId !== 1" @click="doDel(scope.row.roleId)">删除</el-link>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination
                    class="mt-4"
                    v-show="total > 0"
                    :total="total"
                    v-model:page="queryParams.page"
                    v-model:limit="queryParams.pageSize"
                    @pagination="getList"
                />
            </div>
        </div>
        <Add v-model="visible" v-if="visible" :keyId="keyId" :isView="isView" @update="resetQuery"></Add>
    </div>
</template>

<script setup>
import Add from './add.vue';
import { queryRoleList, deleteRole } from '@/api/role';

const { proxy } = getCurrentInstance();

// 获取列表
const queryParams = reactive({
    page: 1,
    pageSize: 10,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
getList();
function getList() {
    loading.value = true;
    queryRoleList(queryParams).then(res => {
        console.log(res,'kkkkres');
        list.value = res.list;
        total.value = +res.total;
    }).finally(() => {
        loading.value = false;
    });
}

// 添加
const visible = ref(false);
const keyId = ref('');
const isView = ref(false);
function doAdd(row, view) {
    console.log(row,'row');
    isView.value = view;
    if (row && row.roleId) {
        keyId.value = row.roleId;
    } else {
        keyId.value = '';
    }
    visible.value = true;
}

// 删除
function doDel(roleId) {
    
    ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        deleteRole(roleId).then(() => {
            proxy.$message.success('操作成功');
            getList();
        })
    }).catch(() => { });
}

// 重置
function resetQuery() {
    queryParams.pageSize = 10;
    queryParams.page = 1; // 页码还原
    getList(); // 更新列表
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}
</style>
