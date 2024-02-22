<template>
  <div class="login">
    <div class="login-main">
      <div class="login-title">重庆公车在线</div>
      <div>-企业服务平台-</div>
      <div class="formbox">
        <div class="formbox-title">账号登录</div>
        <el-form ref="loginRef" size="large" :model="form" :rules="rules">
          <el-form-item
            prop="userMobile"
            :rules="[
              { required: true, message: '手机号码不能为空', trigger: 'blur' },
              {
                pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                message: '请输入正确的手机号码',
                trigger: 'blur',
              },
            ]"
          >
            <el-input
              v-model="form.userMobile"
              placeholder="请输入你的手机号"
            />
          </el-form-item>
          <el-form-item
            prop="password"
            :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]"
          >
            <el-input
              type="password"
              v-model="form.password"
              placeholder="请输入密码"
              show-password
            ></el-input>
          </el-form-item>
          <el-form-item>
            <dragVerify v-model:value="form.isVerifyPass"></dragVerify>
          </el-form-item>
        </el-form>
        <!-- <div style="color: #333;">{{ form.isVerifyPass }}</div> -->
        <div class="formbox-initialpassword">初始密码为：ddzl123</div>
        <div class="formbox-btn">
          <el-button
            type="primary"
            size="large"
            @click="doLogin"
            :loading="loading"
            >登录</el-button
          >
        </div>
        <div class="formbox-forget">
          <el-button type="text" @click="forgetpsw">忘记密码</el-button>
          <el-button type="text" @click="downloadapp">APP下载</el-button>
        </div>
      </div>
    </div>
    <div class="login-footer">
      Copyright © 2017-2023 xxxx公司  渝ICP备xxxx号-1  版权所有
    </div>
    <Forgetpwd v-model="forgetpswvisible"></Forgetpwd>
    <Downloadapp v-model="appvisible"></Downloadapp>
    <initialPassWord v-model="visible" :userId="userId" :mobile="mobile"></initialPassWord>
  </div>
</template>

<script setup>
import useUserStore from "@/store/modules/user";
import Forgetpwd from "./forgetpwd.vue";
import Downloadapp from "./downloadApp.vue";
import dragVerify from "./dragVerify.vue";
import initialPassWord from "./initialPassWord.vue";
const userStore = useUserStore();
const router = useRouter();
const route = useRoute();
const query = route.query;
const form = reactive({
  userMobile: "",
  password: "",
  isVerifyPass: false, // 滑块验证结果
});
const rules = {
  userMobile: [
    { required: true, message: "请输入手机号码", trigger: "blur" },
    {
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: "请输入正确的手机号码",
      trigger: "blur",
    },
  ],
  password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  // code: [{ required: true, trigger: "change", message: "请输入验证码" }]
};
const loading = ref(false);
const { proxy } = getCurrentInstance();
import { ElMessage} from "element-plus";

const mobile = ref('');
const userId = ref('');
const visible=ref(false);

function doLogin() {
  proxy.$refs.loginRef.validate((valid) => {
    if (valid && form.isVerifyPass) {
      const password = proxy.sha256(form.password);
      const param = {
        userMobile: form.userMobile,
        password,
      };
      loading.value = true;
      userStore
        .login(param)
        .then((res) => {
          mobile.value = res.userInfo.userMobile;
          userId.value = res.userInfo.userId;
          const hasPwd = res && res.userInfo && res.userInfo.hasPwd;
            // visible.value = true;
            if (hasPwd === 1) {
                visible.value = true;
            } else {
              sessionStorage.setItem("token", res.token);
                const path = query && query.redirect ? query.redirect : '/';
                router.replace(path);
            }
        
        }).catch((err) =>{
          console.log(err,'====')
        })
        .finally(() => {
          loading.value = false;
        });
    } else {
      ElMessage.error('请完成表单验证和滑块验证！');
    }
  });
}
// 忘记密码
const forgetpswvisible = ref(false);
function forgetpsw() {
  forgetpswvisible.value = true;
}
// App下载
const appvisible = ref(false);
function downloadapp() {
  appvisible.value = true;
}
</script>
<style lang="scss" scoped>
.login {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;

  &-main {
    flex: 1;
    background: $theme;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    color: #ffffff;
  }

  &-footer {
    height: 60px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #eeeeee;
    color: #999999;
    font-size: 13px;
  }

  &-title {
    font-size: 36px;
    font-weight: bold;
  }
}

.formbox {
  background: #ffffff;
  border-radius: 6px;
  border: 1px solid #d7d7d7;
  padding: 40px;
  width: 386px;
  margin-top: 40px;

  &-title {
    font-size: 20px;
    color: #333333;
    margin-bottom: 60px;
    text-align: center;
  }
  &-initialpassword {
    color: #999999;
    font-size: 13px;
  }
  &-btn {
    margin-top: 40px;

    .el-button {
      width: 100%;
    }
  }

  &-forget {
    margin-top: 20px;
    display: flex;
    justify-content: space-around;
  }
}
</style>
