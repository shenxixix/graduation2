<template>
    <div>
        <Dialog v-model="visible" title="商家主页" :footerShow="false" :closeOnModal="false" :padding="0">
            <div class="p-6 bg-gray-50">
                <div class="bg-white p-4 rounded leading-10">
                    <div class="grid grid-cols-3 gap-4 mt-4 mb-4">
                        <el-image
                            style="height: 150px"
                            fit="cover"
                            :src="item"
                            :preview-teleported="true"
                            :preview-src-list="stationPics"
                            :initial-index="index"
                            v-for="(item, index) in stationPics"
                            :key="index" />
                    </div>
                    <el-row>
                        <el-col :span="4" class="text-hui">商家名称</el-col>
                        <el-col :span="20">{{ formData.stationName }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">所属平台</el-col>
                        <el-col :span="20">{{ formData.enterpriseName }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">商家类型</el-col>
                        <el-col :span="20">{{ dict('enterpriseType', formData.stationType) }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">门店地址</el-col>
                        <el-col :span="20" class="flex items-center justify-between">
                            <div>{{ formData.location }}</div>
                            <el-button type="primary" text @click="doPosition">查看</el-button>
                        </el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">商家信息</el-col>
                        <el-col :span="20" class="flex items-center justify-between">
                            <div></div>
                            <el-button type="primary" text @click="doInfo">查看</el-button>
                        </el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">商家号</el-col>
                        <el-col :span="20">{{ dict('enterpriseType', formData.stationCode) }}</el-col>
                    </el-row>
                </div>
            </div>
        </Dialog>
        <Info v-model="auditVisible" v-if="auditVisible" :keyId="keyId" />
        <PositionMap
            v-if="positionVisible"
            v-model="positionVisible"
            :info="formData"
        ></PositionMap>
    </div>
</template>

<script setup>
import { findStationInfo } from '@/api/business';
import Info from './info.vue'
import PositionMap from "./positionMap.vue";
// import QRCode from 'qrcode';

const { proxy } = getCurrentInstance();

// 字典
const { enterpriseType, applyStatus } = proxy.dict(['enterpriseType', 'applyStatus']);

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
    if (props.keyId) {
        getInfo();
    }
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});

// 获取详情
const formData = ref({});
// 图片
const stationPics = computed(() => {
    const pics = formData.value.stationPics;
    if (pics) {
        return pics.split(',');
    }
    return [];
});
const authInfo = computed(() => {
    const pics = formData.value.authInfo;
    if (pics) {
        return pics.split(',');
    }
    return [];
});
// const qrUrl = ref('');
function getInfo() {
    findStationInfo(props.keyId).then(res => {
        formData.value = res.data;
        // 二维码
        // QRCode.toDataURL(res.data.stationCode).then(res => {
        //     qrUrl.value = res;
        // })
    });
}

// 查看门店地址
const positionVisible = ref(false);
function doPosition() {
    positionVisible.value = true;
}

const auditVisible = ref(false)
function doInfo() {
    auditVisible.value = true
}
</script>