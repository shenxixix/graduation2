<template>
    <div>
        <Dialog v-model="visible" title="退还详情" :footerShow="false">
                <div class="m-auto w-[375px]">
                    <div class="border-b pb-4 mb-4 flex flex-col items-center justify-center">
                        <div class="mb-4">车辆租赁押金退还</div>
                        <div class="text-[30px] text-black font-bold">+{{ formatMoney(formData.money) }}</div>
                    </div>
                    <div class="leading-[30px]">
                        <el-row>
                            <el-col :span="6" class="text-hui">当前状态</el-col>
                            <el-col :span="18">已存入钱包</el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6" class="text-hui">退款时间</el-col>
                            <el-col :span="18">{{ formatTime(formData.insertTimestamp) }}</el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6" class="text-hui">交易单号</el-col>
                            <el-col :span="18">{{ formData.keyId }}</el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6" class="text-hui">商品信息</el-col>
                            <el-col :span="18">租赁订单 - {{ formData.orderCode }}</el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6" class="text-hui">应退金额</el-col>
                            <el-col :span="18">{{ formatMoney(formData.deposit) }}</el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6" class="text-hui">扣除金额</el-col>
                            <el-col :span="18">{{ formatMoney(formData.deduct) }}</el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="6" class="text-hui">备注</el-col>
                            <el-col :span="18">{{ formData.refundRemark }}</el-col>
                        </el-row>
                    </div>
                </div>
        </Dialog>
    </div>
</template>

<script setup>
import { billDetail } from '@/api/order';

const emit = defineEmits();
const props = defineProps({
    modelValue: {
        type: Boolean,
        default: false
    },
    keyId: {
        type: [String, Number],
        default: ''
    },
});

// 弹窗
const visible = ref(false);
watch(() => props.modelValue, val => {
    visible.value = val;
    getInfo();
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});
const formData = ref({});
function getInfo() {
    if (!props.keyId) return;
    billDetail(props.keyId).then(res => {
        formData.value = res;
    });
}
</script>

<style lang="scss" scoped>
.border-b {
    border-bottom: 1px solid #EDEDED;
}
</style>