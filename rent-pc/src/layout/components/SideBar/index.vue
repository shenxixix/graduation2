<template>
    <div class="menu">
        <el-scrollbar>
            <el-menu :default-active="defaultActive" :collapse="isCollapse" router unique-opened>
                <side-menu
                    v-for="item in menu"
                    :key="item.path"
                    :menu="item"
                    :base-path="item.path"
                ></side-menu>
            </el-menu>
        </el-scrollbar>
    </div>
</template>

<script setup>
import SideMenu from "./SideMenu.vue";
import useUserStore from '@/store/modules/user';

const userStore = useUserStore();
const isCollapse = computed(() => userStore.isCollapse);
const route = useRoute();
const defaultActive = ref('/index');
const menu = computed(() => userStore.menuRoutes);

watchEffect(() => {
    const activeMenu = route.meta.activeMenu;
    defaultActive.value = activeMenu ? activeMenu : route.fullPath.split('?')[0];
});
</script>

<style lang="scss" scoped>
.menu {
    width: max-content;
    height: 100%;
    .el-scrollbar {
        width: max-content;
    }
}
.el-menu:not(.el-menu--collapse) {
    width: 200px;
}
.el-menu {
    min-height: calc(100vh - 60px);
}
.el-menu--collapse .el-sub-menu {
    display: flex;
}
</style>
