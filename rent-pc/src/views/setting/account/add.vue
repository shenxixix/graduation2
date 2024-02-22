<template>
  <div>
    <Dialog
      v-model="visible"
      :title="title"
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
                { required: true, message: '姓名不能为空', trigger: 'blur' },
              ]"
            >
              <el-input
                v-model="formData.userNickName"
                placeholder="请输入姓名"
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
                clearable
              />
            </el-form-item>
            <el-form-item
              label="性别"
              prop="userSex"
              :rules="[
                { required: true, message: '不能为空', trigger: 'blur' },
              ]"
            >
              <el-select
                v-model="formData.userSex"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in userSex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item
              label="角色"
              prop="roleId"
              :rules="[
                { required: true, message: '不能为空', trigger: 'blur' },
              ]"
            >
              <el-select
                v-model="formData.roleId"
                placeholder="请选择"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in roleList"
                  :key="dict.roleId"
                  :label="dict.roleName"
                  :value="dict.roleId"
                />
              </el-select>
              <div class="text-gray-400 text-xs mt-4">初始密码：ddzl123</div>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import { addOrUpdateUser, findUserInfo } from "@/api/user";
import { queryRoleList } from "@/api/role";
import { reactive } from "vue";

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  keyId: {
    type: [String, Number],
    default: "",
  },
});

// 字典
const { userSex } = proxy.dict(["userSex"]);
const queryParams = reactive({
  page: 1,
  pageSize: 10,
});
// 获取角色下拉列表
const roleList = ref([]);
getRoleList();
function getRoleList() {
  queryRoleList(queryParams).then((res) => {
    console.log(res, "kkkk");
    roleList.value = res.list;
  });
}

const confirmLoading = ref(false);
const formData = ref({
  page: 1,
  pageSize: 10,
});
const title = computed(() => (props.keyId ? "修改账号" : "添加账号"));
const visible = ref(false);

watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
    if (props.keyId) {
      getInfo();
    }
  },
  { immediate: true }
);
watch(
  () => visible.value,
  (val) => {
    emit("update:modelValue", val);
  }
);

// 详情
function getInfo() {
  findUserInfo(props.keyId).then((res) => {
    formData.value = res.userInfo;
  });
}

// 提交表单
function confirm() {
  proxy.$refs.queryRef.validate((valid) => {
    if (!valid) {
      return;
    }
    confirmLoading.value = true;
    console.log(formData.value, "提交的form");
    addOrUpdateUser({
        // ...formData.value,
      userNickName: formData.value.userNickName,
      userMobile: formData.value.userMobile,
      userSex: formData.value.userSex,
      roleId: formData.value.roleId,
      roleName: formData.value.roleName,
      userId: formData.value.userId,
    })
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
