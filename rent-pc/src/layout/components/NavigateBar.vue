<template>
    <div class="header">
        <el-row align="middle">
            <el-col :span="8">
                <div class="header-left">
                    <el-icon :size="20" @click="menuCollapse">
                        <Fold v-if="userStore.isCollapse" />
                        <Expand v-else />
                    </el-icon>
                    <el-icon :size="20" @click="toggle"><FullScreen /></el-icon>
                </div>
            </el-col>
            <el-col :span="8">
                <div class="header-title">重庆公车在线-企业服务平台</div>
            </el-col>
            <el-col :span="8">
                <div class="header-right">
                    <img class="header-avatar" :src="userInfo.headImg" alt="">
                    <div class="header-name truncate">你好，{{ userInfo.userNickName }}</div>
                    <el-button size="small" plain @click="logout">退出</el-button>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<script setup>
import { ElMessageBox } from 'element-plus';
import { useFullscreen } from '@vueuse/core';
import useUserStore from '@/store/modules/user';

const userStore = useUserStore();
const userInfo = computed(() => userStore.userInfo);
const { isFullscreen, enter, exit, toggle } = useFullscreen();

function logout() {
    ElMessageBox.confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        // location.href = '/login';
        userStore.logOut().then(() => {
            location.href = '/login';
            sessionStorage.removeItem('token')
        })
    }).catch(() => { });
}
function menuCollapse() {
    userStore.isCollapse = !userStore.isCollapse;
}
</script>
<style lang="scss" scoped>
.header {
    display: flex;
    align-items: center;
    height: 100%;
    width: 100%;
    color: #ffffff;
    .el-row {
        width: 100%;
    }
    &-left {
        display: flex;
        align-items: center;
        .el-icon {
            font-size: 20px;
            cursor: pointer;
            &:hover {
                opacity: .8;
            }
            + .el-icon {
                margin-left: 20px;
            }
        }
    }
    &-title {
        font-size: 20px;
        text-align: center;
    }
    &-right {
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }
    &-name {
        margin: 0 10px;
    }
    &-avatar {
        border-radius: 50%;
        width: 30px;
        height: 30px;
    }
}
</style>
