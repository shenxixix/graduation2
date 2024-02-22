<template>
  <div>
    <Dialog
      v-model="visible"
      title="添加"
      :padding="0"
      @confirm="confirm"
      :confirmLoading="confirmLoading"
    >
      <div class="p-6 bg-gray-50">
        <el-form :model="formData" ref="queryRef" label-position="top">
          <div class="p-4 bg-white rounded leading-10">
            <el-form-item
              label="厂家名称"
              prop="factoryName"
              :rules="[
                { required: true, message: '不能为空', trigger: 'blur' },
              ]"
            >
              <el-input
                v-model="formData.factoryName"
                placeholder="请输入厂家名称"
                clearable
              />
            </el-form-item>
            <el-form-item
              label="手机号"
              prop="contactsMobile"
              :rules="[
                {
                  required: true,
                  message: '手机号码不能为空',
                  trigger: 'blur',
                },
                {
                  pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                  message: '请输入正确的手机号码',
                  trigger: 'blur',
                },
              ]"
            >
              <el-input
                v-model="formData.contactsMobile"
                placeholder="请输入"
                clearable
              />
              <div class="text-gray-400 text-xs mt-4">
                用于登录车辆二维码、设备ID、车架号匹配工具
              </div>
            </el-form-item>
            <el-form-item
              label="初始密码"
              prop="password"
              :rules="[
                { required: true, message: '不能为空', trigger: 'blur' },
              ]"
            >
              <el-input
                v-model="formData.password"
                placeholder="请输入"
                disabled
                clearable
              />
            </el-form-item>
          </div>
        </el-form>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import { addOrUpdateFactory, findFactoryInfo } from "@/api/warehouse";
import { reactive, watch } from "vue";

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  id: {
    type: String,
    default: "",
  },
});
const formData = ref({
    password: "hauwei123",
});
const visible = ref(false);

watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
  },
  { immediate: true }
);
watch(
  () => visible.value,
  (val) => {
    emit("update:modelValue", val);
  }
);
watch(
  () => props.id,
  (val) => {
    if (val) {
      getData();
    }
  },
  { immediate: true }
);

function getData() {
  findFactoryInfo({ keyId: props.id }).then((res) => {
    formData.value = res.data
    formData.value.password = 'huawei123'
    console.log(formData,12345)
  });
}
// 提交表单
const confirmLoading = ref(false);
function confirm() {
  proxy.$refs.queryRef.validate((valid) => {
    if (!valid) {
      return;
    }
    confirmLoading.value = true;
    addOrUpdateFactory(formData.value)
      .then(() => {
        proxy.$message.success("操作成功");
        visible.value = false;
        emit("update");
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  });
}
</script>

<style lang="scss" scoped></style>
