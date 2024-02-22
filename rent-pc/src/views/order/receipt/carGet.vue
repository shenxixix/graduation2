<template>
    <div class="w-[375px]">
        <div class="bg-white p-4 rounded" v-loading="loading">
            <div class="leading-[30px]" v-if="formData['车架号']">
                <el-row v-for="key in Object.keys(formData)" :key="key">
                    <el-col :span="6" class="text-hui">{{ key }}</el-col>
                    <el-col :span="18">{{ formData[key] }}</el-col>
                </el-row>
            </div>
            <div class="leading-[30px]" v-else-if="!loading">
                <div>第一步</div>
                <div>到商家线下门店取车</div>
                <div class="mt-4">第二步</div>
                <div>检查门店工作人员提供的车辆，检查无误后扫码绑定车辆</div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { obtainCarRecord } from '@/api/order';

const props = defineProps({
    keyId: {
        type: [String, Number],
        default: ''
    },
});

const formData = ref({});
const loading = ref(true);
getInfo();
function getInfo() {
    if (!props.keyId) return;
    loading.value = true;
    obtainCarRecord(props.keyId).then(res => {
        formData.value = res.data;
    }).finally(() => {
        loading.value = false;
    });
}
</script>

<style lang="scss" scoped>
.border-b {
    border-bottom: 1px solid #EDEDED;
}
</style>