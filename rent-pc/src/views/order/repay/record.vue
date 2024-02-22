<template>
    <div>
        <Dialog v-model="visible" title="还款记录" :footerShow="false" :padding="0" height="max">
            <div class="bg-gray-50 p-[20px]">
                <div class="w-[375px] m-auto">
                    <div v-for="(item, index) in list" :key="index" class="mb-[10px] bg-white p-4 rounded">
                        <div class="flex items-center justify-between mb-4">
                            <div>{{ dict('paySource', item.paySource) }}</div>
                            <div class="text-hui">{{ formatTime(item.payTime) }}</div>
                        </div>
                        <div class="text-xl text-theme font-bold">￥{{ formatMoney(item.money) }}</div>
                        <div class="text-hui mt-2">已还至{{ formatTime(item.shouldPayTime, '{y}年{m}月{d}日') }}第3期账单</div>
                    </div>
                </div>
            </div>
        </Dialog>
    </div>
</template>

<script setup>
import { recordListOrder } from '@/api/order';

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
    recordListOrder(props.keyId).then(res => {
        list.value = res.records;
        total.value = res.total;
    });
}
</script>