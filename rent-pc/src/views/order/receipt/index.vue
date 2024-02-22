<template>
    <div>
        <Dialog v-model="visible" title="取还单据" :footerShow="false" :padding="0" height="max">
            <div class="bg-gray-50 p-[20px] w-full h-full flex flex-col items-center">
                <el-radio-group v-model="tabActive" class="mb-4">
                    <el-radio-button label="1">租车合同</el-radio-button>
                    <el-radio-button label="2">取车记录</el-radio-button>
                    <el-radio-button label="3">退车记录</el-radio-button>
                </el-radio-group>
                <PdfView v-if="tabActive === '1' && pdfUrl" :url="pdfUrl" />
                <CarGet v-if="tabActive === '2'" :keyId="keyId" />
                <CarBack v-if="tabActive === '3'" :keyId="keyId" />
            </div>
        </Dialog>
    </div>
</template>

<script setup>
import { recordListOrder, findOrderPdf } from '@/api/order';
import CarGet from './carGet.vue';
import CarBack from './carBack.vue';

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

const tabActive = ref('1');

// pdf
const pdfUrl = ref('');
findOrderPdf(props.keyId).then(res => {
    pdfUrl.value = res.pdfUrl;
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