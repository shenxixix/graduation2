<template>
    <router-view class="app-container"></router-view>
</template>

<script setup></script>
<style lang="scss">
* {
    box-sizing: border-box;
}
.app-container {
    display: flex;
    flex-direction: column;
    height: 100vh;
    width: 100vw;
    overflow: auto;
}
</style>
