<template>
    <div class="p-32 text-2xl">
        <div class="mb-2">{{ userInfo.userNickName }}，欢迎您登入</div>
        <div>重庆公车在线-企业服务平台”</div>
    </div>
</template>

<script setup>
import useUserStore from '@/store/modules/user';

const userStore = useUserStore();
const userInfo = computed(() => userStore.userInfo);
</script>

<style lang="scss" scoped></style>
