<template>
    <el-menu-item
        v-if="!menu.children || !menu.children.length"
        :index="resolvePath(menu.path, basePath)"
    >
        <!-- <el-icon :size="16" style="margin-right: 6px">
            <component :is="menu.icon" />
        </el-icon> -->
        <svg-icon :name="menu.menuIcon" :color="route.path === menu.path ? variable.theme : ''" />
        <template #title>{{ menu.title }}</template>
    </el-menu-item>
    <el-sub-menu
        :index="resolvePath(menu.path)"
        v-else
    >
        <template #title>
            <!-- <el-icon :size="16" style="margin-right: 6px">
                <component :is="menu.icon" />
            </el-icon> -->
            <svg-icon :name="menu.menuIcon" :color="route.path.includes(menu.path) ? variable.theme : ''" />
            <span>{{ menu.title }}</span>
        </template>
        <side-menu
            v-for="child in menu.children"
            :key="child.path"
            :menu="child"
            :base-path="menu.path"
        ></side-menu>
    </el-sub-menu>
</template>

<script setup>
import variable from '@/assets/styles/variable.module.scss';

const props = defineProps({
    menu: {
        type: Object,
        required: true
    },
    basePath: {
        type: String,
        default: ''
    }
});
const { menu, basePath } = toRefs(props);
const route = useRoute();

const onlyOneChild = ref({});

function hasOneShowingChild(children = [], parent) {
    // When there is only one child router, the child router is displayed by default
    if (children.length === 1) {
        onlyOneChild.value = children[0];
        return true
    }
    // Show parent if there are no child router to display
    if (children.length === 0) {
        onlyOneChild.value = { ...parent };
        return true
    }
    return false
};

// 获取全路径
function resolvePath(routePath, parentPath) {
    const getFullPath = path => {
        if (!path) return '';
        const hasBasePath = path.split('')[0] === '/';
        return hasBasePath ? path : `/${path}`;
    };
    if (routePath.includes('/')) {
        // 全路径
        return getFullPath(routePath);
    }
    // 子路径
    return getFullPath(parentPath) + getFullPath(routePath);
}
</script>

<style lang="scss" scoped>
.el-tooltip__trigger .svg-icon {
    margin: 0;
}
.svg-icon {
    margin-right: 16px;
}
</style>
