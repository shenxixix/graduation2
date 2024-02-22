<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>车辆品牌列表</div>
        <el-button type="primary" icon="Plus" @click="handleExport"
          >添加品牌</el-button
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
            label="品牌名称"
            align="center"
            prop="carBrandName"
            :show-overflow-tooltip="true"
            min-width="180"
          >
          </el-table-column>
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
              <el-button type="text" @click="doView(scope.row)" class="change"
                >编辑</el-button
              >
              <el-button type="danger" text='danger' @click="handleDel(scope.row.keyId)" 
                >删除</el-button
              >
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
    <Add v-model="visible" v-if="visible" :data="data" @update="handleUpdate" :flag="flag"></Add>
  </div>
</template>

<script setup>
import { carBrandList, delBrand } from "@/api/car";
import Add from "./editBrand.vue";
const { proxy } = getCurrentInstance();

// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
const data = ref({})
const flag = ref(false)
getList();
function getList() {
  loading.value = true;
  carBrandList(queryParams)
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
function doView(val) {
  flag.value = true
  data.value = val;
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

// 导出
function handleExport() {
  flag.value = false
  visible.value = true
}
function handleUpdate() {
  getList()
}
function handleDel(id) {
  ElMessageBox.confirm("确定删除吗", "提示", {
    confirmButtonText: "删除",
    cancelButtonText: "取消",
    showClose: false,
  })
    .then(() => {
      let params = {
        keyId: id,
      }
      loading.value = true;
      delBrand(params)
        .then((res) => {
          getList()
        })
        .finally(() => {
          loading.value = false;
        });
    })
    .catch(() => {});
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
</style>
