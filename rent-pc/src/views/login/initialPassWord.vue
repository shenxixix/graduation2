im<template>
    <div>
        <Dialog v-model="visible" title="初始密码修改" :footerShow="false" :closeOnModal="false">
            <div class="text-center mx-auto" style="width: 400px">
                <div class="text-lg mb-4">为了你的账户安全，请对初始密码进行修改</div>
                <div class="text-gray-400 text-xs">至少8个字符，不能全是字母或数字。</div>
                <el-steps :space="200" :active="stepActive" process-status="finish" finish-status="process" simple class="mt-10">
                    <el-step title="设置新密码" />
                    <el-step title="设置成功" />
                </el-steps>
                <div v-if="stepActive === 0">
                    <el-form :model="formData" ref="queryRef" label-position="top" class="mt-4">
                        <div class="p-4 bg-white rounded leading-10">
                            <el-form-item label="新密码" prop="userPassword" :rules="[ { required: true, message: '不能为空', trigger: 'blur' }, { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$).{8,}$/, message: '格式错误', trigger: 'blur' } ]">
                                <el-input type="password" v-model="formData.userPassword" placeholder="请输入密码" show-password></el-input>
                            </el-form-item>
                            <el-form-item label="确认新密码" prop="password" :rules="[ { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$).{8,}$/, message: '格式错误', trigger: 'blur' }, { required: true, validator: passWordCheck, trigger: 'blur' } ]">
                                <el-input type="password" v-model="formData.password" placeholder="请输入密码" show-password></el-input>
                            </el-form-item>
                        </div>
                    </el-form>
                    <el-button style="width: 200px;" type="primary" size="large" @click="doNext" :loading="loading">下一步</el-button>
                </div>
                <div v-else>
                    <div class="bg-gray-50 p-10 mt-4 mb-10">
                        <el-icon size="40" color="#17B909"><CircleCheck /></el-icon>
                        <div class="text-green-600 mt-2">密码设置成功，请使用新密码重新登录</div>
                    </div>
                    <el-button style="width: 200px;" type="primary" size="large" @click="doLogin" :loading="loginLoading">重新登录</el-button>
                </div>
            </div>
        </Dialog>
    </div>
</template>

<script setup>
import { updatePassword } from '@/api/login';
import useUserStore from '@/store/modules/user';

const { proxy } = getCurrentInstance();
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();
const query = route.query;

const emit = defineEmits();
const props = defineProps({
    modelValue: {
        type: Boolean,
        default: false
    },
    mobile: {
        type: [Number, String],
        default: ''
    },
    userId: {
        type: [Number, String],
        default: ''
    },
});

// 弹窗
const visible = ref(false);
watch(() => props.modelValue, val => {
    visible.value = val;
},{ immediate: true });
watch(() => visible.value, val => {
    emit('update:modelValue', val);
});

const formData = ref({
    userPassword: '',
    password: ''
});

// 步骤
const stepActive = ref(0);
const loading = ref(false);

function doNext() {
    proxy.$refs.queryRef.validate(valid => {
        if (!valid) {
            return;
        }
        loading.value = true;
        const userPassword = proxy.sha256(formData.value.userPassword);
        updatePassword({ userPassword,userId:props.userId, }).then(() => {
            stepActive.value++;
        }).finally(() => {
            loading.value = false;
        })
    })
}

// 校验密码
function passWordCheck(rule, value, callback) {
    if (!value) {
        callback(new Error("不能为空"))
    } else if (value && value !== formData.value.userPassword) {
        callback(new Error("两次输入不一致"))
    } else {
        callback();
    }
}


// 重新登录
const loginLoading = ref(false);
function doLogin() {
    const password = proxy.sha256(formData.value.userPassword);
    const param = {
        userMobile: props.mobile,
        // userId:props.userId,
        password
    };
    loginLoading.value = true;
    userStore.login(param).then(() => {
        const path = query && query.redirect ? query.redirect : '/';
        router.replace(path);
    }).finally(() => {
        loginLoading.value = false;
    });
}
</script>