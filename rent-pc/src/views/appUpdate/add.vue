<template>
  <div>
    <Dialog
      v-model="visible"
      title="发布版本"
      :closeOnModal="false"
      @confirm="confirm"
      :confirmLoading="confirmLoading"
    >
      <el-form :model="formData" ref="queryRef" label-position="top">
        <el-form-item
          label="版本号"
          prop="appVersion"
          :rules="[
            { required: true, validator: versionCheck, trigger: 'blur' },
          ]"
        >
          <el-input
            v-model="formData.appVersion"
            placeholder="请输入"
            clearable
          />
          <div class="text-gray-400 text-xs mt-2">
            版本号必须使用X.X.X.XX的四段格式填写，X为整数数字，例如1.3.2.01。
          </div>
        </el-form-item>
        <el-form-item
          label="更新方式"
          prop="updateType"
          :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]"
        >
          <el-radio-group
            v-model="formData.updateType"
            @change="formData.stationIdList = []"
          >
            <el-radio
              :label="item.value"
              v-for="(item, index) in updateType"
              :key="index"
              >{{ item.label }}</el-radio
            >
          </el-radio-group>
          <div class="text-gray-400 text-xs mt-2">
            选择更新方式用户在收到提示后可选择更新或不更新；强制更新则会强制用户进行更新，否则无法使用。
          </div>
        </el-form-item>
        <el-form-item
          label="更新内容"
          prop="description"
          :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]"
        >
          <el-input
            type="textarea"
            v-model="formData.description"
            placeholder="请输入"
            clearable
            maxlength="120"
            show-word-limit
          />
        </el-form-item>
        <el-form-item width="400px"
          label="APK包"
          prop="apkUrl"
          :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]"
        >
          <!-- v-if="isAndroid" -->
          <el-upload
            v-model:file-list="fileList"
            action="/fileserver/upload"
            :limit="1"
            :before-upload="beforeUpload"
            :on-success="onSuccess"
            :on-remove="onRemove"
          >
            <el-button type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">只能上传APK文件</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="版本升级提示弹窗示例">
          <div class="w-full flex justify-between items-center">
            <div class="flex-1 flex flex-col items-center">
              <el-image
                style="width: 255px; height: 348px"
                class="rounded"
                fit="cover"
                :src="appType0"
              ></el-image>
              <div class="mt-4">{{ proxy.dict("updateType", 0) }}</div>
            </div>
            <div class="flex-1 flex flex-col items-center">
              <el-image
                style="width: 255px; height: 348px"
                class="rounded"
                fit="cover"
                :src="appType1"
              ></el-image>
              <div class="mt-4">{{ proxy.dict("updateType", 1) }}</div>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup>
import { queryAppVersion, addOrUpdAppVersion } from "@/api/appUpdate";
import appType0 from "../../assets/images/appUpdate/appType_0.svg";
import appType1 from "../../assets/images/appUpdate/appType_1.svg";
import { ElMessage } from "element-plus";
const { proxy } = getCurrentInstance();

// 字典
const { updateType } = proxy.dict(["updateType"]);

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
  appType: {
    type: [Number, String],
    default: 0,
  },
});

// const isAndroid = computed(() => +props.appType === 0 || +props.appType === 2);

// 表单
const confirmLoading = ref(false);
const formData = ref({
  appVersion: "",
  updateType: 0,
  description: "",
  // appType: props.appType
  appType: 0,
});
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

// 上传
const fileList = ref([]);
function beforeUpload(file) {
  console.log(file, "fffffile");
  const isAPK = file.type === "application/vnd.android.package-archive";
  if (!isAPK) {
    ElMessage({
      message: "文件格式错误.",
      type: "error",
    });
    return false;
  }
  formData.value.fileName = file.name;
  formData.value.appSize = Math.floor((file.size / 1024 / 1024) * 10) / 10;
}
function onSuccess(res) {
  console.log(res, "rrrrrrrsw");
  formData.value.apkUrl = res[0].url;

}






function onRemove() {
  formData.value.apkUrl = "";
}

// 获取详情
function getInfo() {
  queryAppVersion({ keyId: props.keyId, page: 1, pageSize: 10 }).then((res) => {
    formData.value = res;
    // if (isAndroid) {
    //     fileList.value = [{
    //         name: res.fileName,
    //         url: res.apkUrl
    //     }];
    // }
    fileList.value = [
      {
        name: res.fileName,
        url: res.apkUrl,
      },
    ];
  });
}

// 版本号校验
// function versionCheck(rule, value, callback) {
//    const versionRegex = /^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)$/;
//    if (!value) {
//        callback(new Error("不能为空"))
//    } else if (!versionRegex.test(value)) {
//        callback(new Error("格式错误"))
//    } else {
//        callback();
//    }
//   if (!value) {
//     callback(new Error("不能为空"));
//   } else {
//     callback();
//   }
// }
function versionCheck(rule, value, callback) {
  const versionRegex = /^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(0|[1-9]\d*)$/;
  if (!value) {
    callback(new Error("不能为空"));
  } else if (!versionRegex.test(value)) {
    callback(new Error("格式错误"));
  } else {
    callback();
  }
}
// 提交表单
function confirm() {
  proxy.$refs.queryRef.validate((valid) => {
    if (!valid) {
      return;
    }
    confirmLoading.value = true;
    addOrUpdAppVersion(formData.value)
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

<style lang="scss" scoped>
.el-upload-list__item{
  width: 120% !important;
}
</style>
