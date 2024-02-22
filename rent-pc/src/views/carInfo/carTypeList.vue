<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>车辆类型列表</div>
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
            label="类型名称"
            align="center"
            prop="carTypeName"
            :show-overflow-tooltip="true"
            min-width="180"
          >
          </el-table-column>
        </el-table>
        <div class="mt-2">注：以上数据由“公车在线”平台统一管理，若需添加数据请联系市机关事务局</div>
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
  </div>
</template>

<script setup>
import { carTypeList } from "@/api/car";
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
  carTypeList(queryParams)
    .then((res) => {
      list.value = res.records;
      total.value = +res.total;
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
.change{
  color: rgb(0, 128, 255);
}
</style>
