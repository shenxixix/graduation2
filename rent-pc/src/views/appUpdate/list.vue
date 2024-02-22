<template>
    <div class="min-h-full flex flex-col p-4 bg-gray-50">
        <div class="bg-white flex-1 p-4">
            <div>
                <el-button type="primary" icon="Plus" @click="doAdd">发布版本</el-button>
            </div>
            <div class="mt-4">
                <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
                    <el-table-column fixed label="版本号" align="center" prop="appVersion" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column  label="应用大小（MB）" align="center" prop="appSize" :show-overflow-tooltip="true" min-width="150" />
                    <el-table-column  label="APK文件" align="center" prop="fileName" min-width="200" />
                    <el-table-column label="更新方式" align="center" :show-overflow-tooltip="true" min-width="150">
                        <template #default="scope">
                            <dict-tag :options="updateType" :value="scope.row.updateType" />
                        </template>
                    </el-table-column>
                    <el-table-column label="发布时间" align="center" :show-overflow-tooltip="true" min-width="180">
                        <template #default="scope">
                            <span>{{ formatTime(scope.row.createTime) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="更新内容" align="center" prop="description" min-width="200">
                        <template #default="scope">
                            <!--  style="white-space: pre-line; line-height: normal; text-align: left;" -->
                            <div v-html="scope.row.description"></div>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" width="180" fixed="right">
                        <template #default="scope">
                            <div class="flex items-center justify-center">
                                <el-link  :underline="false" :href="scope.row.apkUrl">下载</el-link>
                                <el-link type="success" :underline="false" @click="doAdd(scope.row)">编辑</el-link>
                                <el-link type="danger" :underline="false" @click="doDel(scope.row.keyId)">删除</el-link>
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
        <Add v-model="visible" v-if="visible" :appType="appType" :keyId="keyId" @update="resetQuery"></Add>
    </div>
</template>

<script setup>
import Add from './add.vue';
import { appVersionList, deleteAppVersion } from '@/api/appUpdate';
import { onMounted } from 'vue';

const { proxy } = getCurrentInstance();

// 字典
const { updateType } = proxy.dict(['updateType']);

const props = defineProps({
    appType: {
        type: [Number, String],
        default: 0
    },
});

// const isAndroid = computed(() => +props.appType === 0 || +props.appType === 2);

// 获取列表
const queryParams = reactive({
    page: 1,
    pageSize: 10,
    // appType: +props.appType,
    appType:0
});

const loading = ref(false);
const list = ref([]);
const total = ref(0);

// 接收外部传入的查询条件
// watch(() => props.appType, val => {
//     console.log(val,'===val');
//     queryParams.appType = +val;
//     getList();
// }, { immediate: true });

// 列表
function getList() {
    loading.value = true;
    appVersionList(queryParams).then(res => {
        list.value = res.records;
        total.value = +res.total;
    }).finally(() => {
        loading.value = false;
    });
}

// 重置
function resetQuery() {
    queryParams.pageSize = 10;
    queryParams.page = 1; // 页码还原
    getList(); // 更新列表
}

// 添加
const visible = ref(false);
const keyId = ref('');
function doAdd(row) {
    console.log(row.keyId,'修改');
    if (row && row.keyId) {
        keyId.value = row.keyId;
    } else {
        keyId.value = '';
    }
    visible.value = true;
}

// 删除
function doDel(keyId) {
    ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        deleteAppVersion({keyId:keyId}).then(() => {
            proxy.$message.success('操作成功');
            getList();
        })
    }).catch(() => { });
}

// 下载
function doDownload(row) {
    console.log(row);
}
onMounted(()=>{
    getList();
});
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}
</style>
