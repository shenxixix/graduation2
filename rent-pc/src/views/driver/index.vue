<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
        <el-form-item label="名字" prop="userNickName">
          <el-input v-model="queryParams.userNickName" style="width: 240px" />
        </el-form-item>
        <el-form-item label="手机号" prop="userMobile">
          <el-input v-model="queryParams.userMobile" style="width: 240px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>账号列表</div>
        <div>
          <el-button type="primary" icon="Plus" @click="handleAdd">添加</el-button>
          <el-button type="primary" icon="Download" @click="handleImport">导入</el-button>
        </div>
      </div>
      <div class="mt-4">
        <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
          <el-table-column label="姓名" align="center" prop="userNickName" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="手机号" align="center" prop="userMobile" :show-overflow-tooltip="true" min-width="180" />
          <el-table-column label="性别" align="center" prop="userSex" :show-overflow-tooltip="true" min-width="200" >
          <template #default="scope">
              <div>{{ scope.row.userSex == 0 ? '男':'女' }}</div>
            </template>
          </el-table-column>
          <el-table-column label="准驾车型" align="center" prop="approvedModel" :show-overflow-tooltip="true" min-width="180">
          </el-table-column>
          <el-table-column label="操作" align="center" min-width="180" fixed="right">
            <template #default="scope">
              <div class="flex items-center justify-center">
                <el-link type="primary" :underline="false" @click="handleAdd(scope.row)">修改</el-link>
                <el-button type="danger" text='danger' @click="handleDel(scope.row.keyId)"
                >删除</el-button
              >
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination class="mt-4" v-show="total > 0" :total="total" v-model:page="queryParams.current"
          v-model:limit="queryParams.size" @pagination="getList" />
        <div class="text-gray-400 text-sm mt-4">
          <div>注：</div>
          <div class="flex-center">1、列表中添加的驾驶员，可使用APP端完成出车、还车操作。<el-link type="primary" :underline="false"
              @click="handleDown">点我下载</el-link></div>
          <div>2、手机号及APP端登录账号，初始密码：ddzl123</div>
        </div>
      </div>
    </div>
    <Add v-model="visible" v-if="visible" @update="handleQuery" :data="rowData"></Add>
    <Down v-model="downVisible" v-if="downVisible"></Down>
    <ImportData v-model="importVisible" v-if="importVisible" @update="resetQuery"></ImportData>
  </div>
</template>

<script setup>
import { fixDriverList, deleteFixDriver } from "@/api/driver";
import useUserStore from "@/store/modules/user";
import Add from './add.vue';
import Down from "./down.vue"
import ImportData from './importData.vue'
const { proxy } = getCurrentInstance();
const userStore = useUserStore();
const userInfo = computed(() => userStore.userInfo);
// 字典
const { bindStatus } = proxy.dict(["bindStatus"]);

// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
  userMobile: '',
  userNickName: ''
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
getList();
function getList() {
  loading.value = true;
  fixDriverList(queryParams)
    .then((res) => {
      list.value = res.records;
      total.value = +res.total;
    })
    .finally(() => {
      loading.value = false;
    });
}
const downVisible = ref(false)
function handleDown() {
  downVisible.value = true
}

// 添加
const visible = ref(false);
const rowData = ref({})
function handleAdd(val) {
  rowData.value = val
  visible.value = true;
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

const importVisible = ref(false)
function handleImport() {
  importVisible.value = true
}

function handleDel(id) {
  deleteFixDriver({keyId: id})
    .then((res) => {
      proxy.$message.success("操作成功");
      handleQuery()
    })
    .finally(() => {
      loading.value = false;
    });
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
.flex-center {
  display: flex;
  align-items: center;
}
</style>
