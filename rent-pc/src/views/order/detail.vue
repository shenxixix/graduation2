<template>
    <div>
        <Dialog v-model="visible" title="订单详情" :footerShow="false" :closeOnModal="false" :padding="0">
            <div class="bg-gray-50 p-4 flex flex-col items-center justify-center">
                <div class="w-[375px]">
                    <div class="bg-white p-4 rounded mb-4" v-if="formData.isFinishPay === 1 && formData.status === 2">
                        <div class="flex items-center text-orange-500">
                            <el-icon class="mr-3" :size="40">
                                <eleme-filled />
                            </el-icon>
                            <div class="flex-1 flex-wrap">退租申请已通过审核，请于2022-02-03前将车辆退回取车点，未按时退还车辆，我们将按规收取违约金</div>
                        </div>
                    </div>
                    <div class="bg-white p-4 rounded mb-4" v-if="formData.quitTime">
                        <div class="flex items-center text-orange-500">
                            <el-icon class="mr-3" :size="40">
                                <eleme-filled />
                            </el-icon>
                            <div class="flex-1 flex items-center">
                                <div>退出成功</div>
                            </div>
                        </div>
                    </div>
                    <div class="bg-white p-4 rounded mb-4">
                        <div class="flex items-center justify-between mb-4 pb-4 border-b">
                            <div>订单编号：{{ formData.orderId }}</div>
                            <DictTag name="orderStatus" :value="formData.status" />
                        </div>
                        <div class="flex items-start mb-4 pb-4 border-b">
                            <el-image
                                fit="cover"
                                :src="formData.goodsPic"
                                :preview-teleported="true"
                                :preview-src-list="[formData.goodsPic]"
                                class="w-[70px] h-auto mr-[15px]"
                            ></el-image>
                            <div class="flex-1">
                                <div class="text-black text-base mb-2">{{ formData.goodsName }}</div>
                                <div class="text-hui text-xs mb-4">
                                    <span class="mr-2">{{ formData.carBrand }}</span>
                                    <span>{{ formData.carModel }}</span>
                                </div>
                                <el-tag type="info" v-if="formData.carId">车辆：{{ formData.din }}({{ formData.plate }})</el-tag>
                                <div v-if="formData.status === 7" class="text-hui text-sm">超时未支付押金，订单被自动取消</div>
                            </div>
                        </div>
                        <div class="flex items-center justify-between mb-4 pb-4 border-b">
                            <div class="flex items-center">
                                <el-icon class="mr-2">
                                    <office-building />
                                </el-icon>
                                <div>{{ formData.stationName }}</div>
                            </div>
                            <div class="h-[30px] border-l pl-3 flex items-center">
                                <el-link :underline="false" type="info" @click="detailStationVisible = true">门店详情</el-link>
                            </div>
                        </div>
                        <div>{{ formatTime(formData.startTime, '{y}-{m}-{d}') }} 至 {{ formatTime(formData.endTime, '{y}-{m}-{d}') }} 共{{ formData.term }}个月</div>
                    </div>
                    <div class="bg-white p-4 rounded mb-4">
                        <div class="mb-4 pb-4 border-b">
                            <div class="flex items-center justify-between mb-3">
                                <div class="text-black">费用明细</div>
                                <div class="flex items-center text-hui cursor-pointer hover:opacity-80" @click="planRepayVisible = true">
                                    <div class="mr-2">
                                        <span>还款计划</span>
                                        <template v-if="formData.status === 3">
                                            <span>，</span>
                                            <span class="text-orange-500">有逾期</span>
                                        </template>
                                    </div>
                                    <el-icon><ArrowRightBold /></el-icon>
                                </div>
                            </div>
                            <div class="flex items-center justify-between mb-3">
                                <div>车辆租赁费</div>
                                <div class="flex items-center">
                                    <span class="text-hui">￥{{ formatMoney(formData.singleMoney) }} * {{ formatMoney(formData.term) }}月 = </span>
                                    <span>￥{{ formatMoney(formData.totalMoney) }}</span>
                                </div>
                            </div>
                            <div class="flex items-center justify-between mb-3">
                                <div>车辆租赁押金</div>
                                <div class="flex items-center">￥{{ formatMoney(formData.deposit) }}</div>
                            </div>
                            <div class="flex items-center justify-between mb-3">
                                <div>车辆租赁押金退还</div>
                                <div class="flex items-center">-￥{{ formatMoney(formData.refundDeposit) }}</div>
                            </div>
                            <div class="flex items-center justify-end" v-if="formData.quitDetailId">
                                <el-link :underline="false" type="info" @click="detailRepayVisible = true">退还详情</el-link>
                            </div>
                        </div>
                        <div class="flex items-center justify-between">
                            <div>总计</div>
                            <div>￥{{ formatMoney(formData.totalMoney) }}</div>
                        </div>
                    </div>
                    <div class="bg-white p-4 rounded mb-4">
                        <div class="text-black mb-3">承租人</div>
                        <div class="flex items-center justify-between mb-3">
                            <div>姓名</div>
                            <div>{{ formData.realName }}</div>
                        </div>
                        <div class="flex items-center justify-between">
                            <div>身份证号</div>
                            <div>{{ formData.idCard }}</div>
                        </div>
                    </div>
                    <div class="bg-white p-4 rounded mb-4">
                        <div class="text-black mb-3">订单信息</div>
                        <div class="flex items-center justify-between mb-3">
                            <div>订单提交时间</div>
                            <div>{{ formatTime(formData.orderTime) }}</div>
                        </div>
                        <div class="flex items-center justify-between mb-3">
                            <div>车辆签收时间</div>
                            <div>{{ formatTime(formData.checkTime) }}</div>
                        </div>
                        <div class="flex items-center justify-between">
                            <div>车辆退还时间</div>
                            <div>{{ formatTime(formData.quitTime) }}</div>
                        </div>
                    </div>
                    <div class="bg-white p-4 rounded mb-4">
                        <div class="flex items-center justify-between">
                            <div>还款记录</div>
                            <div class="flex-1 flex items-center justify-end cursor-pointer hover:opacity-80" @click="recordRepayVisible = true">
                                <el-icon><ArrowRightBold /></el-icon>
                            </div>
                        </div>
                    </div>
                    <div class="bg-white p-4 rounded">
                        <div class="flex items-center justify-between">
                            <div>取还单据</div>
                            <div class="flex-1 flex items-center justify-end cursor-pointer hover:opacity-80" @click="receiptVisible = true">
                                <el-icon><ArrowRightBold /></el-icon>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </Dialog>
        <StationDetail v-model="detailStationVisible" v-if="detailStationVisible" :keyId="formData.stationId"></StationDetail>
        <PlanRepay v-model="planRepayVisible" v-if="planRepayVisible" :keyId="formData.orderId" />
        <DetailRepay v-model="detailRepayVisible" v-if="detailRepayVisible" :keyId="formData.quitDetailId" />
        <RecordRepay v-model="recordRepayVisible" v-if="recordRepayVisible" :keyId="formData.orderId" />
        <Receipt v-model="receiptVisible" v-if="receiptVisible" :keyId="formData.orderId" />
    </div>
</template>

<script setup>
import { orderDetail } from '@/api/order';
import StationDetail from '@/views/station/detail.vue';
import PlanRepay from './repay/plan.vue';
import DetailRepay from './repay/detail.vue';
import RecordRepay from './repay/record.vue';
import Receipt from './receipt';

const { proxy } = getCurrentInstance();

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
function getInfo() {
    orderDetail(props.keyId).then(res => {
        formData.value = res;
    });
}

// 门店详情
const detailStationVisible = ref(false);

// 还款计划
const planRepayVisible = ref(false);

// 退还详情
const detailRepayVisible = ref(false);

// 还款记录
const recordRepayVisible = ref(false);

// 取还单据
const receiptVisible = ref(false);

</script>

<style lang="scss" scoped>
.border-b {
    border-bottom: 1px solid #EDEDED;
}
.border-l {
    border-left: 1px solid #EDEDED;
}
</style>