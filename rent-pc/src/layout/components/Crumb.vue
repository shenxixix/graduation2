<template>
    <div class="crumb">
        <el-breadcrumb separator="/">
            <transition-group name="breadcrumb">
                <el-breadcrumb-item to="/" key="/">首页</el-breadcrumb-item>
                <el-breadcrumb-item v-for="item in list" :key="item.path" :to="item.path">{{ item.meta.title }}</el-breadcrumb-item>
            </transition-group>
        </el-breadcrumb>
    </div>
</template>

<script setup>
const route = useRoute();
const list = ref([]);
watchEffect(() => {
    list.value = route.matched.filter(ele => ele.meta && ele.meta.title);
});
</script>

<style lang="scss" scoped>
.crumb {
    height: 40px;
    width: 100%;
    background: #ffffff;
    padding: 0 20px;
    display: flex;
    align-items: center;
    .el-breadcrumb {
        position: relative;
        width: 100%;
        display: inline-block;
        .el-breadcrumb__item {
            display: inline-block;
            width: max-content;
            transition: all .5s;
        }
    }
}
/* breadcrumb transition */
.breadcrumb-enter-to {
    opacity: 1;
    transform: translateX(5px);
}

.breadcrumb-leave-active {
    position: absolute;
    opacity: 0;
    transform: translateX(100%);
    transition: all .5s;
}

.breadcrumb-move {
    transition: all .5s;
}
</style>
