<template>
    <div class="site">
        <el-tabs v-model="tabActive">
            <!-- <el-tab-pane label="骑手(iOS)" name="1"></el-tab-pane>
            <el-tab-pane label="骑手(Android)" name="0"></el-tab-pane> -->
            <!-- <el-tab-pane label="iOS" name="3"></el-tab-pane> -->
            <el-tab-pane label="Android" name="2"></el-tab-pane>
        </el-tabs>
        <el-scrollbar class="flex-1">
            <List :appType="tabActive"></List>
        </el-scrollbar>
    </div>
</template>

<script setup>
import List from './list.vue';

const tabActive = ref('1');
</script>

<style lang="scss" scoped>
.site {
    height: 100%;
    display: flex;
    flex-direction: column;
    :deep(.el-tabs__header) {
        margin: 0;
    }
    :deep(.el-tabs__nav) {
        padding: 0 20px;
    }
}
</style>
