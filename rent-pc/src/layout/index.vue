<template>
    <el-container>
        <el-header>
            <navigate-bar></navigate-bar>
        </el-header>
        <el-container style="overflow: auto;">
            <el-aside width="auto">
                <side-bar></side-bar>
            </el-aside>
            <el-main>
                <Crumb v-if="route.path !== '/index'"></Crumb>
                <el-scrollbar>
                    <router-view v-slot="{ Component, route }" style="width: 100%">
                        <!-- <transition name="fade-transform" mode="out-in" appear> -->
                            <component :key="route.fullPath" :is="Component" />
                        <!-- </transition> -->
                    </router-view>
                </el-scrollbar>
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup>
import NavigateBar from "./components/NavigateBar.vue";
import SideBar from "./components/SideBar/index.vue";
import Crumb from './components/Crumb.vue';

const route = useRoute();
</script>

<style lang="scss" scoped>
.el-header {
    background: $theme;
    height: 60px;
}
.el-main {
    display: flex;
    flex-direction: column;
    padding: 0 ;
    background: #ffffff;
}
.el-scrollbar {
    flex: 1;
    box-sizing: border-box;
    // background: #fff;
    margin: 0;
    padding: 0;
}
:deep(.el-scrollbar__view) {
    height: 100%;
}

/* fade */
.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.28s;
}

.fade-enter,
.fade-leave-active {
    opacity: 0;
}

/* fade-transform */
.fade-transform--move,
.fade-transform-leave-active,
.fade-transform-enter-active {
    transition: all .5s;
}

.fade-transform-leave-active {
    position: absolute;
}

.fade-transform-enter {
    opacity: 0;
    transform: translateX(-30px);
}

.fade-transform-leave-to {
    opacity: 0;
    transform: translateX(30px);
}

</style>
