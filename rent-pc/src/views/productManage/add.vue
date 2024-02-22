<template>
  <div>
    <Dialog v-model="visible" title="添加产品" :footerShow="false" :closeShow="true" :padding="0">
      <div class="container p-4 bg-white">
        <el-form :model="queryParams" label-width="120px" label-position="top" ref="ruleFormRef" >
          <div class="con">
            <el-form-item label="车辆型号" :required="true" prop="carModelId" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-select v-model="queryParams.carModelId" placeholder="请选择" filterable @change="handleChange">
                <el-option v-for="dict in carModel" :key="dict.keyId" :label="dict.carModelName" :value="dict.keyId" />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆品牌" :required="true" prop="carBrandName" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-input v-model="queryParams.carBrandName" disabled/>
            </el-form-item>
            <el-form-item label="车辆类型" :required="true" prop="carTypeName" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-input v-model="queryParams.carTypeName" disabled/>
            </el-form-item>
            <div class="rent">
              <el-form-item label="日租费区间" prop="dayMinFee" :rules="[
              { required: true, message: '请输入', trigger: 'blur' },
            ]">
                <el-input v-model="queryParams.dayMinFee" />
              </el-form-item>
              <span class="icon">-</span>
              <el-form-item label="" prop="dayMaxFee" :rules="[
              { required: true, message: '请输入', trigger: 'blur' },
            ]">
                <el-input v-model="queryParams.dayMaxFee" />
              </el-form-item>
            </div>
            <el-form-item label="产品特色" :required="true">
              <el-table ref="singleTableRef" :data="carParams" style="width: 100%; margin-top: 20px" border>
                <el-table-column label="特色名称" min-width="150" align="center" prop="key" />
                <el-table-column label="操作" align="center" width="150" fixed="right">
                  <template #default="scope">
                    <el-button type="text" @click="handleBaseParamsEdit(scope)" class="change">编辑</el-button>
                    <el-button type="text" @click="handleBaseParamsDel(scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div @click="handleAdd" class="add mt-4" v-if="carParams.length<4">添加产品特色</div>
              <el-dialog v-model="paramDialog" style="padding-top: 30px">
                <el-form :model="formParams" label-width="120px" label-position="top" ref="form">
                  <div class="con">
                    <el-form-item label="特色名" :required="true" prop="key" :rules="[
                      { required: true, message: '请输入', trigger: 'blur' },
                    ]">
                      <el-input v-model="formParams.key" maxlength="6"/>
                    </el-form-item>
                    <el-form-item class="btn">
                      <el-button type="primary" plain @click="paramDialog = false">取消</el-button>
                      <el-button type="primary" @click="handleAddParam()">确定</el-button>
                    </el-form-item>
                  </div>
                </el-form>
              </el-dialog>
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
import { carBrandList, carTypeList, carModelList, viewCarModel } from "@/api/car";
import { addOrUpdateProduct, fixProductDetail } from '@/api/product'
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
const queryParams = ref({
});
const visible = ref(false);
const carBrand = ref([]);
const carType = ref([]);
const carParams = ref([
  {key: '1年车龄'},
  {key: '倒车影像'}
]);
const configParams = ref([]);
const paramDialog = ref(false);
const editFlag = ref(false);
const paramIndex = ref(0);
const btnLoading = ref(false)
const carModel = ref([])

const startRules =(rule, value, callback) => {
      if(value > queryParams.value.dayMaxFee) {
        console.log(value,queryParams.value.dayMinFee,123)
        callback(new Error('不能大于最大值'))
      } else {
        callback()
      }
    }
const endRules = (rule, value, callback) => {
      console.log(value<queryParams.value.dayMinFee,456)
      if(value < queryParams.value.dayMinFee) {
        callback(new Error('不能小于最小值'))
      } else {
        callback()
      }
    }

const rules = reactive({
  dayMinFee: [{ required: true, validator: startRules, trigger: 'blur' }],
  dayMaxFee: [{ required: true, validator: endRules, trigger: 'blur' }],
})
watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
    console.log(props.keyId,5)
    if(props.keyId) {
      getData()
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


function getData() {
  fixProductDetail({keyId: props.keyId}).then((res) => {
      queryParams.value.carModelId = res.carModelId
      queryParams.value.dayMinFee = res.dayMinFee/100
      queryParams.value.dayMaxFee = res.dayMaxFee/100
      carParams.value = res.configParams
      queryParams.value.keyId = res.keyId
      handleChange(res.carModelId)
    })
    .finally(() => { });
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
  console.log(val,123)
  viewCarModel({ keyId: val })
    .then((res) => {
      //carModel.value = res.records;
      queryParams.value.carBrandName = res.carBrandName
      queryParams.value.carTypeName = res.carTypeName
      queryParams.value.carBrandId = res.carBrandId
      queryParams.value.carTypeId = res.carTypeId
    })
    .finally(() => { });
}

const dialogImageUrl = ref("");
const imgList = ref([]);

const formParams = ref({
  key: "",
  value: "",
});
const configFormParams = ref({
  key: "",
  value: "有",
});
const dialogVisible = ref(false);

function cancel() {
  visible.value = false;
}
function handleAdd() {
  formParams.value = {}
  editFlag.value = false;
  paramDialog.value = true;
}
function handleBaseParamsEdit(data) {
  paramIndex.value = data.$index;
  console.log(paramIndex, 123)
  formParams.value = data.row;
  editFlag.value = true;
  paramDialog.value = true;
}

function handleBaseParamsDel(data) {
  carParams.value.splice(data.$index, 1);
  console.log(data);
}
function handleAddParam() {
  proxy.$refs.form.validate((valid) => {
    if (!valid) {
      return;
    }
    let obj = { key: formParams.value.key};
    if (!editFlag.value) {
      carParams.value.push(obj);
    } else {
      carParams.value.splice(paramIndex.value, 1, obj);
    }
    proxy.$refs.form.resetFields();
    paramDialog.value = false;
  });
}
function submitForm() {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (!valid) {
      return;
    }
    if (carParams.value.length == 0) {
      ElMessage({
        type: "error",
        message: "请添加产品特色",
      });
      return;
    }
    console.log(queryParams.value,999)
    if (Number(queryParams.value.dayMinFee) > Number(queryParams.value.dayMaxFee)) {
      proxy.$message.error("后面的数值需大于等于前面的数值");
      return
    }
    queryParams.value.configParams = carParams.value;
    let obj = JSON.parse(JSON.stringify(queryParams.value))
    obj.dayMaxFee = obj.dayMaxFee*100
    obj.dayMinFee = obj.dayMinFee*100
    btnLoading.value = true;
    addOrUpdateProduct(obj)
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
