<template>
    <div class="min-h-full flex flex-col p-4 bg-gray-50">
        <div class="p-4 pb-0 bg-white">
            <el-form :model="queryParams" ref="queryRef" :inline="true">
                <el-form-item label="姓名" prop="userName">
                    <el-input v-model="queryParams.userName" placeholder="请输入" clearable style="width: 240px" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                    <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="bg-white flex-1 p-4 mt-4">
            <div class="flex justify-between items-center">
                <div>账号列表</div>
                <div class="flex items-center">
                    <el-button type="primary" icon="Plus" @click="doAdd">添加账号</el-button>
                </div>
            </div>
            <div class="mt-4">
                <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
                    <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
                        <template #default="scope">
                            <span>{{ scope.$index + 1 }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="姓名" align="center" prop="userNickName" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="手机号" align="center" prop="userMobile" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="性别" align="center" prop="sex" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <span>{{  scope.row.sex==1?'男':'女' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="角色" align="center" prop="roleName" :show-overflow-tooltip="true" min-width="150" />
                    <!-- <el-table-column label="账号状态" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <DictTag name="userAccountStatus" :value="scope.row.userAccountStatus" />
                        </template>
                    </el-table-column> -->
                    <!-- <el-table-column label="创建人" align="center" prop="creator" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column label="创建时间" align="center" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ parseTime(scope.row.gmtCreate) }}</span>
                        </template>
                    </el-table-column> -->
                    <el-table-column label="操作" align="center" min-width="200" fixed="right">
                        <template #default="scope">
                            <div class="flex items-center justify-center">
                                <el-link type="success" :underline="false" @click="doAdd(scope.row)">修改</el-link>
                                <!-- <el-link
                                    :type="scope.row.userAccountStatus === 1 ? 'danger' : 'success'"
                                    :underline="false"
                                    @click="doStatus(scope.row)"
                                >{{ scope.row.userAccountStatus === 1 ? '暂停' : '启用' }}</el-link>
                                <el-link type="primary" :underline="false" @click="doResetPw(scope.row.userId)">重置</el-link> -->
                                <el-link type="danger" :underline="false" @click="doDel(scope.row.userId)">删除</el-link>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="remark">添加的账号，请使用初始密码：ddzl123登录</div>
                <pagination
                    class="mt-4"
                    v-show="total > 0"
                    :total="total"
                    v-model:page="queryParams.page"
                    v-model:limit="queryParams.pageNum"
                    @pagination="getList"
                />
            </div>
        </div>
        <Add v-model="visible" v-if="visible" :keyId="keyId" @update="resetQuery"></Add>
    </div>
</template>

<script setup>
import Add from './add.vue';
import { queryUserList, deleteUser, updateStatus, resetPassword } from '@/api/user';

const { proxy } = getCurrentInstance();

// 字典
const { userSex, userAccountStatus } = proxy.dict(['userSex', 'userAccountStatus']);

// 获取列表
const queryParams = reactive({
    page: 1,
    pageNum: 10,
    userName: undefined,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
getList();
function getList() {
    loading.value = true;
    queryUserList(queryParams).then(res => {
        list.value = res.list;
        total.value = +res.total;
    }).finally(() => {
        loading.value = false;
    });
}

// 添加
const visible = ref(false);
const keyId = ref('');
function doAdd(row) {
    if (row && row.userId) {
        keyId.value = row.userId;
    } else {
        keyId.value = '';
    }
    visible.value = true;
}

// 账户状态
const statusLoading = ref(false);
function doStatus(row) {
    if (statusLoading.value) {
        return;
    }
    const userAccountStatus = row.userAccountStatus === 1 ? 0 : 1;
    const p = {
        userId: row.userId,
        userAccountStatus
    };
    statusLoading.value = true;
    updateStatus(p).then(() => {
        row.userAccountStatus = userAccountStatus;
        proxy.$message.success('操作成功');
    }).finally(() => {
        statusLoading.value = false;
    })
}

// 重置密码
function doResetPw(id) {
    ElMessageBox.confirm('确定将登录密码重置为“ddzl123”？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        resetPassword(id).then(() => {
            proxy.$message.success('操作成功');
        })
    }).catch(() => { });
}

// 删除
function doDel(id) {
    ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        deleteUser(id).then(() => {
            proxy.$message.success('操作成功');
            getList();
        })
    }).catch(() => { });
}

// 重置
function resetQuery() {
    queryParams.pageNum = 10;
    queryParams.page = 1; // 页码还原
    proxy.$refs.queryRef.resetFields(); // 表单还原
    getList(); // 更新列表
}

// 搜索
function handleQuery() {
    queryParams.page = 1; // 查询第一页
    getList(); // 更新列表
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}
.remark{
    margin-top:20px ;
    font-size: 15px;
    color: #999;
}
</style>
