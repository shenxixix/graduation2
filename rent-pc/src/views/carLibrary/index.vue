<template>
  <div>
    <el-tabs v-model="tabActive">
      <el-tab-pane label="车辆型号" name="1"></el-tab-pane>
      <el-tab-pane label="车辆品牌" name="2"></el-tab-pane>
      <el-tab-pane label="车辆类型" name="3"></el-tab-pane>
    </el-tabs>
    <div class="flex-1">
      <CarModeList v-if="tabActive === '1'"></CarModeList>
      <CarBrandList v-if="tabActive === '2'"></CarBrandList>
      <CarTypeList v-if="tabActive === '3'"></CarTypeList>
    </div>
  </div>
</template>
<script setup>
import CarModeList from './carModeList.vue';
import CarBrandList from './carBrandList.vue';
import CarTypeList from './carTypeList.vue';
const tabActive = ref("1");
</script>
<style lang="scss" scoped>
:deep(.el-tabs__header) {
  margin: 0;
}
:deep(.el-tabs__nav) {
  padding: 0 20px;
}
</style>
