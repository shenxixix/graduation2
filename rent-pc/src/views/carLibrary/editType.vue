<template>
  <div>
    <Dialog
      v-model="visible"
      title="添加车辆类型"
      :footerShow="false"
      :closeShow="true"
      
      :padding="0"
    >
      <div class="container p-4 bg-white">
        <el-form
          :model="queryParams"
          label-width="120px"
          label-position="top"
          ref="ruleFormRef"
        >
          <div class="con">
            <el-form-item
              label="车辆类型"
              :required="true"
              prop="carTypeName"
              :rules="[{ required: true, message: '请输入', trigger: 'blur' }]"
            >
              <el-input v-model="queryParams.carTypeName" />
            </el-form-item>
            <el-form-item
              label="排序"
              :required="true"
              prop="sortNum"
              :rules="[{ required: true, message: '请输入', trigger: 'blur' }]"
            >
              <el-input v-model="queryParams.sortNum" onkeyup="this.value=this.value.replace(/\D|^0/g,'')" />
            </el-form-item>
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
import { addOrUptCarType } from "@/api/car";
const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  data: {
    type: [Object],
    default: {},
  },
  flag: {
    type: [Boolean],
    default: false
  }
});
const visible = ref(false);
const loading = ref(false);
const queryParams = ref({
});
watch(() => props.modelValue, val => {
    visible.value = val;
    if (props.flag) {
      console.log(props.data,888)
        queryParams.value = props.data
    }
},{ immediate: true });
watch(
  () => visible.value,
  (val) => {
    emit("update:modelValue", val);
  }
);


function cancel() {
  proxy.$refs.ruleFormRef.resetFields();
  visible.value = false;
}

function submitForm() {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (!valid) {
      return;
    }
    loading.value = true;
    addOrUptCarType(queryParams.value)
      .then(() => {
        proxy.$message.success("操作成功");
        visible.value = false;
        emit("update");
      })
      .finally(() => {
        loading.value = false;
      });
  });
}
</script>
<style lang="scss" scoped>
:deep(.el-select) {
  width: 100%;
}
:deep(.btn .el-form-item__content) {
  justify-content: end;
}
:deep(.el-form-item) {
  margin-bottom: 30px;
}
// :deep(.el-form-item) {
//   margin-bottom: 10px;
// }
.container {
  min-height: calc(90vh - 110px);
}
.con {
  padding: 0 70px;
  .title {
    font-weight: 600;
    color: #eb5b31;
  }
}
.select {
  width: 100%;
  height: 100px;
  border: 1px dashed #eb5b31;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #eb5b31;
}
.clear {
  color: #eb5b31;
  cursor: pointer;
}
.action {
  align-items: center;
  margin-right: 10px;
  cursor: pointer;
}
.add {
  cursor: pointer;
  color: #eb5b31;
  margin: 0 auto;
}
</style>
