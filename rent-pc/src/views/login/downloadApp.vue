<template>
  <Dialog v-model="visible" title="APP下载" width="20%" :footerShow="false">
    <div style="display: flex;justify-content: space-around;">
      <img :src="download"  alt="" style="width: 200px; height: 200px;">
    </div>
</Dialog>
</template>

<script setup>
// import QRCodeVue3 from "qrcode-vue3";
import download from "@/assets/images/download.png";
 
</script>

<style >

</style>


