<template>
  <Dialog v-model="visible" title="忘记密码" width="500px">
    <div class="main">
      <div class="main-rule">至少8个字符，不能全是字母或数字</div>
      <el-form
        ref="loginForm"
        label-position="top"
        label-width="100px"
        :model="formdata"
      >
        <el-form-item
          label="登录账号"
          prop="userMobile"
          :rules="[
            { required: true, message: '账号不能为空', trigger: 'blur' },
            {
              pattern:
                /^(13[0-9]|14[5-9]|15[0-3,5-9]|166|17[0-8]|18[0-9]|19[1,8-9])\d{8}$/,
              message: '手机号格式不正确',
              trigger: 'blur',
            },
          ]"
        >
          <el-input v-model="formdata.userMobile" placeholder="请输入手机号" />
        </el-form-item>

        <el-form-item
          label="验证码"
          prop="code"
          :rules="[
            { required: true, message: '验证码不能为空', trigger: 'blur' },
          ]"
        >
          <div class="main-code">
            <el-input v-model="formdata.code" placeholder="请输入" />
            <el-button type="primary" :disabled="disabled" @click="getCode">
              {{ buttonText }}
            </el-button>
          </div>
          <div class="main-sms" v-if="formdata.userMobile.length === 11">
            将向{{ hideMiddleDigits(formdata.userMobile) }}发送短信验证码
          </div>
        </el-form-item>

        <el-form-item
          label="新密码"
          prop="newPassword"
          :rules="[ { required: true, message: '不能为空', trigger: 'blur' }, { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$).{8,}$/, message: '格式错误', trigger: 'blur' } ]"
        >
          <el-input
            v-model="formdata.newPassword"
            placeholder="请输入新密码"
            show-password
          />
        </el-form-item>

        <el-form-item
          label="确认新密码"
          prop="confirmPassword"
          :rules="[ { pattern: /^(?![0-9]+$)(?![a-zA-Z]+$).{8,}$/, message: '格式错误', trigger: 'blur' }, { required: true, validator: passWordCheck, trigger: 'blur' } ]"
        >
          <el-input
            v-model="formdata.confirmPassword"
            placeholder="请再次输入新密码"
            show-password
          />
        </el-form-item>

        <!-- <div class="main-notmatch" v-if="passwordsNotMatch">
          输入的密码不一致，请检查后重试
        </div> -->
      </el-form>
    </div>
    <template #footer>
      <el-button type="primary" @click="suregologin" :loading="loginLoading"
        >确认并登录</el-button
      >
    </template>
  </Dialog>
</template>

<script setup>
import { ref } from "vue";
import { authcode, updPswAndLogin } from "@/api/login";
const router = useRouter();
const route = useRoute();

const formdata = ref({
  name: "",
  userMobile: "",
});
// 发送短信电话号码隐藏
function hideMiddleDigits(phoneNumber) {
  const trimmedPhoneNumber = phoneNumber.replace(/\D/g, ""); // 去除非数字字符
  if (trimmedPhoneNumber.length <= 7) {
    return trimmedPhoneNumber; // 如果长度不足，返回原始电话号码
  }
  const firstThreeDigits = trimmedPhoneNumber.slice(0, 3);
  const lastFourDigits = trimmedPhoneNumber.slice(-4);
  const middleDigits = "*".repeat(trimmedPhoneNumber.length - 7);
  const formattedPhoneNumber = firstThreeDigits + middleDigits + lastFourDigits;
  return formattedPhoneNumber;
}
// 获取验证码
const buttonText = ref("获取验证码");
const disabled = ref(false);
const remainingTime = ref(60);
// 校验密码
function passWordCheck(rule, value, callback) {
    if (!value) {
        callback(new Error("不能为空"))
    } else if (value && value !== formdata.value.newPassword) {
        callback(new Error("两次输入不一致"))
    } else {
        callback();
    }
}
function getCode() {
  console.log("获取验证码！");
  authcode({
    codeType: 0,
    reqType: 2,
    userMobile: formdata.value.userMobile,
  }).then((res) => {
    console.log(res, "=====");
    disabled.value = true;
    countdown();
  });
}
const passwordsNotMatch = computed(() => {
  return (
    formdata.value.confirmPassword &&
    formdata.value.newPassword !== formdata.value.confirmPassword
  );
});
watch(
  [formdata],
  () => {
    // 在输入完毕后比对
    if (formdata.value.newPassword && formdata.value.confirmPassword) {
      // 执行比对逻辑，例如显示提示信息
      console.log("Perform password comparison logic here");
    }
  },
  { deep: true }
);

const visible = ref(true);
const { proxy } = getCurrentInstance();

const query = route.query;

// 确认并登录
const loginLoading = ref(false);
function suregologin() {
  proxy.$refs.loginForm.validate((valid) => {
    if (valid) {
      loginLoading.value = true;

      updPswAndLogin({
        account: formdata.value.userMobile,
        code: formdata.value.code,
        newPassword: proxy.sha256(formdata.value.newPassword),
        source: 0,
      })
        .then((res) => {
          console.log(res, "=====");
          if (res.status === 1) {
            sessionStorage.setItem("token", res.token);
            const path = query && query.redirect ? query.redirect : "/";
            router.replace(path);
          }
        })
        .finally(() => {
          loginLoading.value = false;
        });
    }
  });
}

function countdown() {
  const timer = setInterval(() => {
    buttonText.value = `${remainingTime.value}s 后重新获取`;
    remainingTime.value--;

    if (remainingTime.value < 0) {
      clearInterval(timer);
      buttonText.value = "获取验证码";
      // this.request("authcode", { userMobile: this.formdata.userMobile }).then(
      //   (res) => {
      //     console.log(res, "验证码");
      //   }
      // );
      disabled.value = false;
      remainingTime.value = 60;
    }
  }, 1000);
}
</script>

<style lang="scss" scoped>
.main {
  max-width: 500px;

  &-rule {
    text-align: center;
  }

  &-code {
    display: flex;
    width: 440px;
  }

  &-sms {
    font-size: 12px;
    color: #999;

    span {
      margin-left: 20px;
      color: #135db3;
    }
  }

  &-notmatch {
    font-size: 12px;
    color: red;
  }
}
</style>
