<template>
    <div>
        <div @click="doTrigger">
            <el-image v-if="modelValue" style="height: 148px; width: 148px;" fit="cover" :src="modelValue" />
            <div v-else class="flex items-center justify-center p-4 rounded border border-dashed border-theme" style="height: 148px; width: 148px;">
                <el-icon color="#426ef3">
                    <Plus />
                </el-icon>
                <span class="text-theme">上传商品照片</span>
            </div>
        </div>
        <Dialog
            v-model="visible"
            title="选择照片"
            @confirm="confirm"
            :closeOnModal="false">
            <div class="grid grid-cols-4 gap-4">
                <div
                    v-for="(item, index) in carImgArrs"
                    :key="index"
                    style="height: 148px"
                    class="border p-2 rounded"
                    :class="{ 'border-theme': item === fileUrl }"
                    @click="imgSelect(item)"
                >
                    <el-image style="height: 100%; width: 100%;" fit="cover" :src="item" />
                </div>
                <el-upload
                    action="/fileserver/upload"
                    list-type="picture-card"
                    :show-file-list="false"
                    :on-success="onSuccess"
                >
                    <el-icon><Plus /></el-icon>
                </el-upload>
            </div>
        </Dialog>
    </div>
</template>

<script setup>
import { viewCarModel } from '@/api/car';

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
    modelValue: {
        type: String,
        default: ''
    },
    keyId: {
        type: [String, Number],
        default: ''
    },
    beforTrigger: Function,
});

const fileUrl = ref('');
const fileList = ref([]);

// 弹窗
const visible = ref(false);
async function doTrigger() {
    const beforTrigger = await props.beforTrigger();
    if (beforTrigger === false) {
        return;
    }
    visible.value = true;
    if (props.keyId) {
        getInfo();
    }
}
watch(() => props.modelValue, val => {
    fileUrl.value = val;
}, { immediate: true });

// 获取详情
const carImgArrs = ref([]);
function getInfo() {
    viewCarModel({ keyId: props.keyId }).then(res => {
        carImgArrs.value = res.carImgArrs.concat(fileList.value);
        if (fileUrl.value && !carImgArrs.value.includes(fileUrl.value)) {
            carImgArrs.value.push(fileUrl.value);
            fileList.value.push(fileUrl.value);
        }
    });
}

// 上传
function onSuccess(res) {
    const url = res[0].url;
    carImgArrs.value.push(url);
    fileList.value.push(url);
}

// 选择照片
function imgSelect(url) {
    fileUrl.value = url;
}

// 确定
function confirm() {
    if (!fileUrl.value) {
        proxy.$message.warning('请选择一张照片');
        return;
    }
    emit('update:modelValue', fileUrl.value);
    visible.value = false;
}
</script>