<template>
  <div>
    <Dialog v-model="visible" title="添加车辆" :footerShow="false" :closeShow="true" :padding="0">
      <div class="container p-4 bg-white">
        <el-form :model="queryParams" label-width="120px" label-position="top" ref="ruleFormRef">
          <div class="con">
            <el-form-item label="车牌号" :rules="[
              { required: true, message: '请输入', trigger: 'blur' },
            ]" prop="license">
              <el-input v-model="queryParams.license" />
            </el-form-item>
            <el-form-item label="车辆型号" :required="true" prop="carModelId" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-select v-model="queryParams.carModelId" placeholder="请选择" filterable @change="handleChange">
                <el-option v-for="dict in carModel" :key="dict.keyId" :label="dict.carModelName" :value="dict.keyId" />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆品牌" :rules="[
              { required: true, message: '请选择', trigger: 'blur' },
            ]" prop="carBrandName">
              <el-input v-model="queryParams.carBrandName" disabled />
            </el-form-item>
            <el-form-item label="车辆类型" :rules="[
              { required: true, message: '请选择', trigger: 'blur' },
            ]" prop="carTypeName">
              <el-input v-model="queryParams.carTypeName" disabled />
            </el-form-item>
            <el-form-item label="能源类型" :rules="[
              { required: true, message: '请选择', trigger: 'blur' },
            ]" prop="energyTypeName">
              <el-input v-model="queryParams.energyTypeName" disabled />
            </el-form-item>
            <el-form-item label="注册日期" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]" prop="registerDate">
              <el-date-picker v-model="queryParams.registerDate" type="date" format="YYYY/MM/DD" value-format="x"
                style="width:100%" />
            </el-form-item>
          </div>
        </el-form>
      </div>
      <template #footer>
        <el-button type="primary" plain @click="cancel()">取消</el-button>
        <el-button type="primary" :loading="btnLoading" @click="submitForm()">确定</el-button>
      </template>
    </Dialog>
  </div>
</template>
<script setup>
import { ElMessage, ElMessageBox } from "element-plus";
import { reactive, watch } from "vue-demi";
import { carBrandList, carTypeList, carModelList, viewCarModel, addOrUpdateFixCar } from "@/api/car";
import { addOrUpdateProduct } from '@/api/product'
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
});

const queryParams = reactive({
});
const visible = ref(false);
const carBrand = ref([]);
const carType = ref([]);
const btnLoading = ref(false)
const carModel = ref([])
watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
    if(props.data.carId) {
      init()
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

function init() {
  console.log(props.data,88)
  queryParams.license = props.data.license
  queryParams.carModelId = props.data.carModelId
  queryParams.registerDate = props.data.registerDate
  queryParams.carId = props.data.carId
  //queryParams = props.data
  handleChange(queryParams.carModelId)
}

initData();
function initData() {
  carBrandList({exportType: 1 })
    .then((res) => {
      carBrand.value = res.records;
    })
    .finally(() => { });
  carTypeList({exportType: 1 })
    .then((res) => {
      carType.value = res.records;
    })
    .finally(() => { });
  carModelList({exportType: 1 })
    .then((res) => {
      carModel.value = res.records;
    })
    .finally(() => { });
}

function handleChange(val) {
  viewCarModel({ keyId: val })
    .then((res) => {
      //carModel.value = res.records;
      queryParams.carBrandName = res.carBrandName
      queryParams.carTypeName = res.carTypeName
      queryParams.carBrandId = res.carBrandId
      queryParams.carTypeId = res.carTypeId
      queryParams.energyTypeName = res.energyTypeName
      queryParams.energyType = res.energyType
    })
    .finally(() => { });
}

function cancel() {
  visible.value = false;
}


function submitForm() {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (!valid) {
      return;
    }
    btnLoading.value = true;
    addOrUpdateFixCar(queryParams)
      .then(() => {
        proxy.$message.success("操作成功");
        visible.value = false;
        emit("update");
      })
      .finally(() => {
        btnLoading.value = false;
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

.rent {
  display: flex;
  align-items: flex-end;
  margin-bottom: 30px;

  :deep(.el-form-item) {
    flex: 1;
    margin-bottom: 0;
  }

  .icon {
    width: 50px;
    line-height: 30px;
    text-align: center;
  }
}


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
