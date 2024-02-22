<template>
  <div>
    <Dialog
      v-model="visible"
      title="导入"
      :footerShow="false"
      :padding="0"
    >
      <div class="container p-4 bg-white">
        <el-form
          :model="form"
          label-width="120px"
          label-position="top"
          ref="ruleFormRef"
        >
          <div class="con">
            <el-upload
              class="upload-demo"
              drag
              ref="upload"
              action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
              :limit="1"
              :before-upload="beforeUpload"
              :http-request="importFile"
              @click="handleUp"
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text text-xs">
                <div class="text-xs">(支持格式：xls、xlsx)</div>
                <div class="mt-2 text-xs">也可直接拖拽到此处上传</div>
              </div>
              <template #tip>
                <div
                  class="el-upload__tip flex"
                  style="color: #eb5b31; cursor: pointer"
                >
                  <el-icon><Download /></el-icon>
                  <a :href="modeXls" download>下载导入模板</a>
                </div>
              </template>
            </el-upload>
          </div>
        </el-form>
      </div>
      <template #footer>
        <el-button type="primary" plain @click="cancel()">取消</el-button>
        <el-button type="primary" @click="submitForm()" :loading="loading">确定</el-button>
      </template>
    </Dialog>
  </div>
</template>
<script setup>
import { ElMessage, ElMessageBox } from "element-plus";
import { reactive, watch } from "vue-demi";
import { importCar } from "@/api/car";
import { getToken } from "@/utils/auth";
import modeXls from "@/assets/static/车辆导入模版.xlsx?url";
import axios from "axios";
const { proxy } = getCurrentInstance();
const emit = defineEmits();
const upFile = ref(null);
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  type: {
    type: [String, Number],
    default: 1,
  },
});
const visible = ref(false);
const loading = ref(false)
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

const form = reactive({
  remarks: "",
});
function handleConfirm() {
  console.log(form);
}
function cancel() {
  visible.value = false;
}
function importFile(param) {
  console.log(param, 777);
  upFile.value = param.file;
}
function handleUp() {
  console.log(123);
  upFile.value = null;
  proxy.$refs.upload.clearFiles();
}
function beforeUpload(file) {
  const type = [
    "application/vnd.ms-excel",
    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
  ];
  if (type.indexOf(file.type) === -1) {
    ElMessage.error("上传文件只支持xls、xlsx格式");
    return false;
  }
}
function handleExport(info) {
  proxy.download(
    "/zoom-fleet-manager/file/downLoadFailFile",
    { fileName: info },
    info
  );
}
function submitForm() {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (!valid) {
      return;
    }
    console.log(upFile.value, 888);
    let formData = new FormData();
    if (!upFile.value) {
      ElMessage({
        type: "error",
        message: "请上传文件",
      });
      return;
    }
    formData.append("file", upFile.value);
    loading.value = true
    importCar(formData).then((res) => {
      if (res.result == 1) {
        ElMessageBox.alert(`成功导入${res.resultNum}条数据`, "", {
          // if you want to disable its autofocus
          // autofocus: false,
          confirmButtonText: "关闭",
          center: true,
          callback: () => {
            visible.value = false;
            emit("update");
          },
        });
      } else {
        ElMessageBox.confirm(
          `成功导入${res.resultNum}条数据,${res.failNum}条失败`,
          "数据导入",
          {
            confirmButtonText: "下载失败数据",
            cancelButtonText: "关闭",
            center: true,
            showClose: false,
            customStyle: "padding:50px 0;",
          }
        )
          .then(() => {
            handleExport(res.errFile);
          })
          .catch(() => {
            upFile.value = null;
            proxy.$refs.upload.clearFiles();
            console.log(upFile.value, 123);
          })
      }
    }).finally(()=>{loading.value = false});;
  });
}
</script>
<style lang="scss" scoped>
:deep(.el-select) {
  width: 100%;
}
:deep(.el-upload) {
  width: 100%;
}
:deep(.el-upload-dragger) {
  width: 100%;
}
:deep(.el-message-box) {
  padding: 50px 0;
}
// :deep(.el-form-item) {
//   margin-bottom: 10px;
// }
.container {
  min-height: calc(90vh - 110px);
}
.con {
  padding: 20px 70px;
  .title {
    font-weight: 600;
    color: #eb5b31;
  }
}
</style>
