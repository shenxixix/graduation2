<template>
    <el-button type="primary" @click="doSms">短信提醒({{ smsText }})</el-button>
    <Dialog v-model="visible" v-if="visible" title="短信提醒" appendToBody @confirm="doConfirm" :confirmLoading="confirmLoading" >
        <el-form :model="formData" ref="queryRef" label-position="top">
            <div class="flex items-center mb-8">
                <div>当有新的{{ title }}时，下列手机号将收到短信推送</div>
                <el-link type="primary" :underline="false" class="ml-4" @click="doDelAll">全部清空</el-link>
            </div>
            <el-form-item
                v-for="(item, index) in formData.list"
                :key="index"
                label=""
                :prop="'list.' + index + '.userMobile'"
                :rules="[{ pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: '请输入正确的手机号码', trigger: 'blur' }]"
            >
                <div class="w-full flex items-center sms-item">
                    <el-input class="flex-1" v-model="item.userMobile" type="number" placeholder="请输入" clearable />
                    <el-icon class="ml-4" color="#f56c6c" size="20" @click="doDel(index)"><Close /></el-icon>
                </div>
            </el-form-item>
            <el-button class="mt-4" type="primary" icon="Plus" @click="doAdd">增加手机号</el-button>
        </el-form>
    </Dialog>
</template>

<script setup>
import { querySmsNoticeList, updateSmsNotice } from '@/api/sms';

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
    // 业务类型 1:订车工单,2:商户审核
    type: {
        type: Number,
        default: 1
    },
    // 当有新的{{ title }}时，下列手机号将收到短信推送
    title: {
        type: String,
        default: ''
    },
});

// 是否设置了短信提醒
const effectMobiles = ref([]); // 有效的手机号List
const smsText = computed(() => {
    const len = effectMobiles.value.length;
    return len > 0 ? '有' : '无'
});

// 弹窗
const visible = ref(false);
function doSms() {
    getList();
    visible.value = true;
}

// 获取列表
const formData = ref({
    list: []
});
const defaultItem = { userMobile: '', bizType: props.type };
function getList() {
    querySmsNoticeList({ bizType: props.type }).then(res => {
        formData.value.list = res.result;
        effectMobiles.value = [ ...res.result ];
        if (!formData.value.list.length) {
            doAdd();
        }
    });
}
onMounted(() => {
    getList();
});

// 添加手机号
function doAdd() {
    formData.value.list.push({ ...defaultItem });
}

// 删除手机号
function doDel(index) {
    formData.value.list.splice(index, 1);
}

// 全部清空
function doDelAll() {
    formData.value.list.forEach(ele => {
        ele.userMobile = '';
    });
}

// 提交
const confirmLoading = ref(false);
function doConfirm() {
    proxy.$refs.queryRef.validate(valid => {
        if (!valid) {
            return;
        }
        const reg = /^1[3|4|5|6|7|8|9][0-9]\d{8}$/;
        const mobiles = formData.value.list.filter(ele => reg.test(ele.userMobile)).map(ele => ele.userMobile);
        console.log('----:', mobiles);
        confirmLoading.value = true;
        updateSmsNotice({ mobiles, bizType: props.type }).then(() => {
            effectMobiles.value = mobiles;
            proxy.$message.success('操作成功');
            visible.value = false;
            emit('change');
        }).finally(() => {
            confirmLoading.value = false;
        })
    });
}
</script>

<style lang="scss" scoped>
.sms-item {
    + .sms-item {
        margin-top: 15px;
    }
    .el-icon {
        cursor: pointer;
        &:hover {
            opacity: .8;
        }
    }
}

</style>