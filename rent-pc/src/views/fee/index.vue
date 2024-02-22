<template>
  <div>
    <el-tabs v-model="tabActive">
      <el-tab-pane label="待结算" name="0"></el-tab-pane>
      <el-tab-pane label="已结算" name="1"></el-tab-pane>
    </el-tabs>
    <div class="flex-1">
      <Wait v-if="tabActive === '0'"></Wait>
      <Done v-if="tabActive === '1'"></Done>
    </div>
  </div>
</template>
<script setup>
import Wait from './wait.vue';
import Done from './done.vue';
const tabActive = ref("0");
</script>
<style lang="scss" scoped>
:deep(.el-tabs__header) {
  margin: 0;
}
:deep(.el-tabs__nav) {
  padding: 0 20px;
}
</style>
