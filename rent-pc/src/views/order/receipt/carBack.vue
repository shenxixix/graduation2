<template>
    <div class="w-[375px]" v-loading="loading">
        <template v-if="formData.deposit">
            <div class="bg-white p-4 rounded mb-[10px]">
                <div class="text-black mb-2">验车说明</div>
                <div class="text-hui">{{ formData.reportRemark }}</div>
                <div class="border-b pb-[20px] mb-[20px]"></div>
                <div class="flex flex-wrap w-full">
                    <div v-for="(item, index) in formData.reportPics" :key="index" class="w-[50%] p-[10px]">
                        <el-image
                            :src="item"
                            class="w-full h-[82px]"
                            fit="cover"
                            :preview-teleported="true"
                            :preview-src-list="formData.reportPics"
                            :initial-index="index"
                        ></el-image>
                    </div>
                </div>
            </div>
            <div class="bg-white p-4 rounded mb-[10px]">
                <div class="text-black mb-2">押金明细</div>
                <div class="leading-[30px]">
                    <el-row>
                        <el-col :span="6" class="text-hui">应退押金</el-col>
                        <el-col :span="18">{{ formatMoney(formData.deposit) }}元</el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6" class="text-hui">验车扣减</el-col>
                        <el-col :span="18">- {{ formatMoney(formData.deduct) }}元</el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6" class="text-hui">实退押金</el-col>
                        <el-col :span="18">{{ formatMoney(formData.refund) }}元</el-col>
                    </el-row>
                </div>
            </div>
            <div class="bg-white p-4 rounded mb-[10px]">
                <div class="text-black mb-2">承租人签字</div>
                <el-image :src="formData.signature"></el-image>
            </div>
        </template>
        <div class="bg-white p-4 rounded leading-[30px]" v-else-if="!loading">
            <div>第一步</div>
            <div>将车辆归还至取车门店</div>
            <div class="mt-4">第二步</div>
            <div>门店工作人员对车辆进行验收，根据验收情况退还押金</div>
        </div>
    </div>
</template>

<script setup>
import { quitCarRecord } from '@/api/order';

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
    quitCarRecord(props.keyId).then(res => {
        formData.value = res;
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