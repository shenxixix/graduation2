<template>
  <div>
    <Dialog
      v-model="visible"
      title="APP下载"
      :padding="0"
      :footerShow="false"
    >
      <div class="p-6 con">
        <img src="@/assets/images/download.png">
        <a :href="imageUrl" download>保存</a>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import { reactive, watch } from "vue";
import imageUrl from "@/assets/static/download.png?url";

const { proxy } = getCurrentInstance();
const emit = defineEmits();

const dialogVisible = ref(false)
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});
const visible = ref(false);

watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
  },
  { immediate: true }
);
watch(
  () => visible.value,
  (val) => {
    emit("update:modelValue", val);
  }
);

function handleQuery(){
  let image = new Image();
  // 解决跨域 canvas污染问题
  image.setAttribute('crossOrigin', 'anonymous')
  image.src = '@/assets/images/download.png' + '?time=' + new Date().valueOf() // 加时间戳
  const canvas = document.querySelector("#canvas");
      canvas.width = image.width
      canvas.height = image.height
      const context = canvas.getContext('2d')
      context.drawImage(image, 0, 0, image.width, image.height)
      canvas.toBlob((blob) => {
        const url = URL.createObjectURL(blob)
        const a = document.createElement('a')
        a.download = '二维码'
        a.href = url
        a.click()
        a.remove()
        URL.revokeObjectURL(url)
      })
}

</script>

<style lang="scss" scoped>
.con {
  display: flex;
  flex-direction: column;
  align-items: center;
  img {
    width: 208px;
    height: 192px;
    margin-bottom: 20px;
    margin-top: 50px;
  }
}
a {
  text-decoration: none;
  padding: 8px 15px;
  background: #426ef3;
  color: #fff;
  border-radius: 3px;
}
</style>
