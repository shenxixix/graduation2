<template>
  <div>
    <Dialog
      v-model="visible"
      title="添加账户"
      :padding="0"
      @confirm="confirm"
      :confirmLoading="confirmLoading"
    >
      <div class="p-6 bg-gray-50">
        <el-form :model="formData" ref="queryRef" label-position="top">
          <div class="p-4 bg-white rounded leading-10">
            <el-form-item
              label="姓名"
              prop="userNickName"
              :rules="[
                { required: true, message: '不能为空', trigger: 'blur' },
              ]"
            >
              <el-input
                v-model="formData.userNickName"
                placeholder="请输入姓名"
                style="width: 70%;"
                clearable
              />
            </el-form-item>
            <el-form-item
              label="手机号"
              prop="userMobile"
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
                v-model="formData.userMobile"
                placeholder="请输入员工手机号"
                style="width: 70%;"
                clearable
              />
            </el-form-item>
            <el-form-item
              label="性别"
              :required="true"
              prop="userSex"
              :rules="[
                { required: true, message: '请选择', trigger: 'change' },
              ]"
            >
              <el-select
                v-model="formData.userSex"
                placeholder="请选择"
                style="width: 70%;"
              >
                <el-option label="男" :value="0" />
                <el-option label="女" :value="1" />
              </el-select>
            </el-form-item>
            <el-form-item
              label="准驾车型"
              prop="approvedModel"
              :rules="[
                { required: true, message: '不能为空', trigger: 'blur' },
              ]"
            >
              <el-input
                v-model="formData.approvedModel"
                placeholder="请输入"
                style="width: 70%;"
                clearable
              />
            </el-form-item>
            <div class="mt-8">初始密码：ddzl123</div>
          </div>
        </el-form>
      </div>
    </Dialog>
    <el-dialog
      v-model="dialogVisible"
      title="提示"
      width="30%"
      :before-close="handleClose"
    >
      <span>确定将登陆密码重置为ddzl123</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogVisible = false">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { addOrUpdateFixDriver } from "@/api/driver";
import { reactive, watch } from "vue";

const { proxy } = getCurrentInstance();
const emit = defineEmits();

const dialogVisible = ref(false)
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  data: {
    type: [Object],
    default: {},
  },
});
const formData = ref({
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
  () => props.data,
  (val) => {
    console.log(val,123)
    if (val.keyId) {
      formData.value = props.data
    }
  },
  { immediate: true }
);

// 提交表单
const confirmLoading = ref(false);
function confirm() {
  proxy.$refs.queryRef.validate((valid) => {
    if (!valid) {
      return;
    }
    //dialogVisible.value = true
    confirmLoading.value = true;
    addOrUpdateFixDriver(formData.value)
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
