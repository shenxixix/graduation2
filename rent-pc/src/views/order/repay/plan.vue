<template>
    <div>
        <Dialog v-model="visible" title="还款计划" :footerShow="false" height="max">
            <el-timeline>
                <el-timeline-item
                    v-for="(item, index) in list"
                    :key="index"
                    placement="top"
                    :timestamp="formatTime(item.shouldPayTime, '{y}-{m}-{d}')"
                    >
                    <div class="text-xs text-hui mb-2">第{{ item.term }}期，{{ dict('repayStatus', item.status) }}</div>
                    <div class="text-black text-lg">￥{{ formatMoney(item.money) }}</div>
                    <div class="text-xs text-orange-500" v-if="item.expiredDays && item.expiredDays > 0">已逾期{{ item.expiredDays }}天，请尽快支付，否则将影响车辆使用</div>
                </el-timeline-item>
            </el-timeline>
        </Dialog>
    </div>
</template>

<script setup>
import { payListOrder } from '@/api/order';

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
    getList();
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});
const list = ref([]);
const total = ref(0);
function getList() {
    if (!props.keyId) return;
    payListOrder(props.keyId).then(res => {
        list.value = res.records;
        total.value = res.total;
    });
}
</script>