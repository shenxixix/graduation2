<template>
    <div>
        <Dialog v-model="visible" :title="title" :closeOnModal="false" :padding="0" @confirm="confirm" :confirmLoading="confirmLoading">
            <div class="p-6 bg-gray-50">
                <el-form :model="formData" ref="queryRef" label-position="top">
                    <div class="p-4 bg-white rounded leading-10">
                        <div class="text-theme mb-2">产品归属</div>
                        <!-- <el-form-item label="商家类型" prop="enterpriseType" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-select
                                v-model="formData.enterpriseType"
                                placeholder="请选择"
                                @change="enterpriseTypeChange"
                                clearable
                                style="width: 100%"
                            >
                                <template v-for="dict in dict('enterpriseType')">
                                    <template v-if="dict.value !== 2">
                                        <el-option
                                            :key="dict.value"
                                            :label="dict.label"
                                            :value="dict.value"
                                        />
                                    </template>
                                </template>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="平台名称" prop="enterpriseId" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-select
                                v-model="formData.enterpriseId"
                                placeholder="请选择"
                                clearable
                                @change="enterpriseIdChange"
                                style="width: 100%"
                            >
                                <el-option
                                    v-for="dict in enterpriseList"
                                    :key="dict.keyId"
                                    :label="dict.enterpriseName"
                                    :value="dict.keyId"
                                />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="商家名称" prop="stationIdList" :rules="[ { required: true, validator: stationCheck, trigger: 'blur' } ]">
                            <div class="flex items-center w-full">
                                <el-radio-group v-model="formData.visibleType" @change="formData.stationIdList = []">
                                    <el-radio :label="1">不限</el-radio>
                                    <el-radio :label="2">指定</el-radio>
                                </el-radio-group>
                                <el-select
                                    v-model="formData.stationIdList"
                                    placeholder="请选择"
                                    multiple
                                    clearable
                                    :disabled="formData.visibleType === 1"
                                    class="flex-1 ml-6"
                                >
                                    <el-option
                                        v-for="dict in stationList"
                                        :key="dict.stationId"
                                        :label="dict.stationName"
                                        :value="dict.stationId"
                                    />
                                </el-select>
                            </div>
                        </el-form-item>
                        <div class="text-gray-400 text-xs mb-4">所选客户或归属客户的骑手可见该产品，若选“不限”及代表所选平台下所有客户。</div> -->
                        <el-form-item label="所属城市" prop="cityCode" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-cascader
                                v-model="cityCode"
                                :separator="separator"
                                :options="provinceAndCityData"
                                :props="cascaderProps"
                                @change="cascaderChange"
                                style="width: 100%;"
                            />
                        </el-form-item>
                    </div>
                    <div class="p-4 bg-white rounded leading-10 mt-4">
                        <div class="text-theme mb-2">产品信息</div>
                        <el-form-item label="产品名称" prop="goodsName" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-input v-model="formData.goodsName" placeholder="请输入" clearable />
                        </el-form-item>
                        <el-form-item label="车辆品牌" prop="carBrand" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-select
                                v-model="formData.carBrand"
                                placeholder="请选择"
                                clearable
                                @change="carBrandChange"
                                filterable
                                style="width: 100%"
                            >
                                <el-option
                                    v-for="dict in carBrandAllList"
                                    :key="dict.keyId"
                                    :label="dict.carBrandName"
                                    :value="dict.keyId"
                                />
                            </el-select>
                            <div class="text-gray-400 text-xs mt-4">
                                没有找到车辆品牌？
                                <span class="text-blue-700 cursor-pointer hover:opacity-80" @click="toCarBrand">点我去添加</span>
                            </div>
                        </el-form-item>
                        <el-form-item label="车辆型号" prop="carModel" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-select
                                v-model="formData.carModel"
                                placeholder="请选择"
                                clearable
                                style="width: 100%"
                            >
                                <el-option
                                    v-for="dict in carModelAllList"
                                    :key="dict.keyId"
                                    :label="dict.carModelName"
                                    :value="dict.keyId"
                                />
                            </el-select>
                            <div class="text-gray-400 text-xs mt-4">
                                没有找到车辆型号？
                                <span class="text-blue-700 cursor-pointer hover:opacity-80" @click="toCarModel">点我去添加</span>
                            </div>
                        </el-form-item>
                        <el-form-item label="商品照片" prop="goodsPic" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <CarImgs v-model="formData.goodsPic" :beforTrigger="beforTrigger" :keyId="formData.carModel"></CarImgs>
                        </el-form-item>
                    </div>
                    <div class="p-4 bg-white rounded leading-10 mt-4">
                        <div class="text-theme mb-2">押金/租金</div>
                        <el-form-item label="押金(元)" prop="deposit" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-input :formatter="value => value.replace(/^(0+)|[^\d]+/g, '')" v-model="formData.deposit" placeholder="请输入" clearable />
                        </el-form-item>
                        <el-form-item label="租金(元/月)" prop="rentMoney" :rules="[ { required: true, message: '不能为空', trigger: 'blur' } ]">
                            <el-input :formatter="value => value.replace(/^(0+)|[^\d]+/g, '')" v-model="formData.rentMoney" placeholder="请输入" clearable />
                        </el-form-item>
                    </div>
                    <div class="p-4 bg-white rounded leading-10 mt-4">
                        <div class="text-theme mb-2">租赁期限</div>
                        <el-form-item label="" prop="tenancyList" :rules="[ { required: true, validator: tenancyCheck, trigger: 'blur' } ]">
                            <el-checkbox-group v-model="tenancy">
                                <el-checkbox label="1-3" size="large" border>3个月</el-checkbox>
                                <el-checkbox label="1-6" size="large" border>6个月</el-checkbox>
                                <el-checkbox label="2-1" size="large" border>1年</el-checkbox>
                                <el-checkbox label="2-2" size="large" border>2年</el-checkbox>
                                <el-checkbox label="2-3" size="large" border>3年</el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                    </div>
                </el-form>
            </div>
        </Dialog>
    </div>
</template>

<script setup>
import { findGoodsInfo, addOrUpdateGoods } from '@/api/order';
import { queryEnterpriseList, queryEnterpriseStationPage } from '@/api/business';
import { carBrandList, carModelList } from '@/api/car';
import { provinceAndCityData, CodeToText } from 'element-china-area-data';
import CarImgs from './carImgs.vue';
import useUserStore from '@/store/modules/user';

const userStore = useUserStore();
const userInfo = computed(() => userStore.userInfo);

const router = useRouter();
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

// 表单
const confirmLoading = ref(false);
const formData = ref({
    enterpriseType: userInfo.value.stationType,
    enterpriseId: userInfo.value.enterpriseId,
    userType: 1,
    goodsPic: '',
    remarks: '',
    rentList: [],
    depositList: [],
    carModel: '',
    carBrand: '',
    stationIdList: [userInfo.value.stationId],
    visibleType: 1,
    goodsMode: 1,
    cityCode: '',
    planList: [],
});
const title = computed(() => props.keyId ? '编辑产品' : '添加产品');
const visible = ref(false);
watch(() => props.modelValue, val => {
    visible.value = val;
    getInfo().then().finally(() => {
        getEnterpriseList();
        getStationList();
        getCarBrandList();
        getCarModelList();
    })
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});

// 去添加车辆型号
function toCarModel(params) {
    router.push('/asset/car?tab=0')
}

// 去添加车辆品牌
function toCarBrand(params) {
    router.push('/asset/car?tab=1')
}

// 选择商家类型
function enterpriseTypeChange() {
    // 清空平台名称
    formData.value.enterpriseId = '';
    enterpriseList.value = [];
    getEnterpriseList();
    // 清空商家名称
    formData.value.stationIdList = [];
    stationList.value = [];
    getStationList();
}

// 选择平台名称
function enterpriseIdChange() {
    formData.value.stationIdList = [];
    stationList.value = [];
    getStationList();
}

// 获取企业下拉列表
const enterpriseList = ref([]);
function getEnterpriseList() {
    if (!formData.value.enterpriseType) {
        return;
    }
    const p = {
        status: 1,
        enterpriseType: formData.value.enterpriseType
    };
    queryEnterpriseList(p).then(res => {
        enterpriseList.value = res.data
    })
}

// 选择车辆品牌
function carBrandChange(val) {
    formData.value.carModel = '';
    carModelAllList.value = [];
    getCarModelList();
}

// 获取车辆品牌下拉列表
const carBrandAllList = ref([]);
function getCarBrandList() {
    carBrandList({exportType: 1 }).then(res => {
        carBrandAllList.value = res.lst;
    })
}

// 获取车辆型号下拉列表
const carModelAllList = ref([]);
function getCarModelList() {
    carModelList({ carBrandId: formData.value.carBrand }).then(res => {
        carModelAllList.value = res.lst;
    })
}

// 获取配送站点下拉列表
const stationList = ref([]);
function getStationList() {
    queryEnterpriseStationPage({ exportType: 1, enterpriseId: formData.value.enterpriseId }).then(res => {
        stationList.value = res.data.records
    })
}

// 选择城市
const separator = '-';
const cityCode = ref('');
const cascaderProps = {
    expandTrigger: 'hover',
}
function cascaderChange(val) {
    if (!val || !val.length) return;
    formData.value.cityCode = val[1];
    formData.value.cityName = `${CodeToText[val[0]]}${separator}${CodeToText[val[1]]}`;
}

// 获取详情
function getInfo() {
    return new Promise((resolve, reject) => {
        if (!props.keyId) {
            resolve();
            return;
        }
        findGoodsInfo(props.keyId).then(res => {
            formData.value = res.data;
            formData.value.deposit = proxy.formatMoney(res.data.deposit);
            formData.value.rentMoney = proxy.formatMoney(res.data.rentMoney);
            tenancy.value = formData.value.tenancyList.map(ele => `${ele.tenancyType}-${ele.tenancy}`);
            cityCode.value = res.data.cityCode + '';
            resolve(res);
        }).catch(err => {
            reject(err);
        })
    })
}

function beforTrigger() {
    if (!formData.value.carModel) {
        proxy.$message.warning('请选择车辆型号');
        return false;
    }
}

// 租期管理
const tenancy = ref([]);
function tenancyCheck(rule, value, callback) {
    const len = tenancy.value.length;
    if (!len) {
        callback(new Error("不能为空"))
    } else {
        callback();
    }
}
// 提交表单
function confirm() {
    proxy.$refs.queryRef.validate(valid => {
        if (!valid) {
            return;
        }
        const param = { ...formData.value };
        param.tenancyList = tenancy.value.map(ele => {
            const values = ele.split('-');
            return {
                tenancy: values[1],
                tenancyType: values[0],
            };
        });
        param.deposit = formData.value.deposit * 100;
        param.rentMoney = formData.value.rentMoney * 100;
        confirmLoading.value = true;
        addOrUpdateGoods(param).then(() => {
            proxy.$message.success('操作成功');
            visible.value = false;
            emit('update');
        }).finally(() => {
            confirmLoading.value = false;
        })
    });
}

function stationCheck(rule, value, callback) {
    const len = formData.value.stationIdList.length;
    if (formData.value.visibleType === 2 && !len) {
        callback(new Error("不能为空"))
    } else {
        callback();
    }
}
</script>

<style lang="scss" scoped>
</style>