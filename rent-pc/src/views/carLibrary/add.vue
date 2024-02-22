<template>
  <div>
    <Dialog v-model="visible" title="添加车辆型号" :footerShow="false" :closeShow="true" :padding="0">
      <div class="container p-4 bg-white">
        <el-form :model="queryParams" label-width="120px" label-position="top" ref="ruleFormRef">
          <div class="con">
            <el-form-item label="车辆型号" :required="true" prop="carModelName"
              :rules="[{ required: true, message: '请输入', trigger: 'blur' }]">
              <el-input v-model="queryParams.carModelName" />
            </el-form-item>
            <el-form-item label="所属品牌" :required="true" prop="carBrandId" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-select v-model="queryParams.carBrandId" placeholder="请选择" filterable>
                <el-option v-for="dict in carBrand" :key="dict.keyId" :label="dict.carBrandName" :value="dict.keyId" />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆类型" :required="true" prop="carTypeId" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-select v-model="queryParams.carTypeId" placeholder="请选择" filterable @change="handleTypeChange">
                <el-option v-for="dict in carType" :key="dict.keyId" :label="dict.carTypeName" :value="dict.keyId" />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆参考样图" :required="true">
              <el-upload v-model:file-list="fileList" list-type="picture-card" :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove" :auto-upload="true" :on-success="handleSucess" action="/fileserver/upload"
                multiple>
                <el-icon>
                  <Plus />
                </el-icon>
              </el-upload>

              <el-dialog v-model="dialogVisible">
                <img w-full :src="dialogImageUrl" alt="Preview Image" />
              </el-dialog>
            </el-form-item>
            <el-form-item label="基本参数" :required="true">
              <el-table ref="singleTableRef" :data="carParams" style="width: 100%; margin-top: 20px" border>
                <el-table-column label="参数项" min-width="150" align="center" prop="key" />
                <el-table-column label="数值" min-width="150" align="center" prop="value">
                  <template #default="scope">
                    <div v-if="scope.row.type == 1"><el-input v-model="scope.row.value" input-style="border:none;" />
                    </div>
                    <div v-else-if="scope.row.type == 2">
                      <div>{{ scope.row.value }}</div>
                    </div>
                    <div v-else>
                      <el-select v-model="scope.row.value" placeholder="请选择">
                        <el-option v-for="dict in energys" :key="dict.value" :label="dict.label" :value="dict.label" />
                      </el-select>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="单位" align="center" width="150" prop="unit">
                </el-table-column>
              </el-table>
            </el-form-item>
            <el-form-item label="配置参数" :required="true">
              <el-table ref="singleTableRef" :data="configParams" style="width: 100%; margin-top: 20px" border>
                <el-table-column label="参数项" min-width="150" align="center" prop="key" />
                <el-table-column label="是否有" min-width="150" align="center" prop="value">
                  <template #default="scope">
                    <el-radio-group v-model="scope.row.value" class="ml-4" :disabled="true">
                      <el-radio label="有" size="large">有</el-radio>
                      <el-radio label="无" size="large">无</el-radio>
                    </el-radio-group>
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center" width="150" fixed="right">
                  <template #default="scope">
                    <el-button type="text" @click="handleConfigParamsEdit(scope)" class="change">编辑</el-button>
                    <el-button type="text" @click="handleConfigParamsDel(scope)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div @click="handleConfigAdd" class="add mt-4">添加参数</div>
              <el-dialog v-model="configParamDialog" style="padding-top: 30px">
                <el-form :model="configFormParams" label-width="120px" label-position="top" ref="configform">
                  <div class="con">
                    <el-form-item label="参数项" prop="key" :rules="[
                      { required: true, message: '请输入', trigger: 'blur' },
                    ]">
                      <el-input v-model="configFormParams.key" />
                    </el-form-item>
                    <el-form-item label="数值" :rules="[
                      { required: true, message: '请选择', trigger: 'change' },
                    ]" prop="value">
                      <el-radio-group v-model="configFormParams.value" class="ml-4">
                        <el-radio label="有" size="large">有</el-radio>
                        <el-radio label="无" size="large">无</el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item class="btn">
                      <el-button type="primary" plain @click="configParamDialog = false">取消</el-button>
                      <el-button type="primary" @click="handleConfigAddParam()">确定</el-button>
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
import { carBrandList, carTypeList, addOrUptCarModel } from "@/api/car";
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
const energys = ref([
    { label: "汽油", value: '1'},
    { label: "柴油", value: '4'},
    { label: "燃气", value: '5'},
    { label: "纯电动", value: '3'},
    { label: "插电式混动", value: '2'},
    { label: "轻混系统 ", value: '6'},
    { label: "氢燃料", value: '7'},
    { label: "增程式 ", value: '8'},
  ])
const visible = ref(false);
const loading = ref(false);
const carBrand = ref([]);
const carType = ref([]);
const fileList = ref([]);
const carParams = ref([
  { key: '车辆类型', value: '', unit: '-', type: 2 },
  { key: '座位数', value: '', unit: '座', type: 1 },
  { key: '车门数', value: '', unit: '门', type: 1 },
  { key: '能源类型', value: '', unit: '-', type: 3 },
]);
const configParams = ref([
  { key: '前驻车雷达', value: '有'},
  { key: '后驻车雷达', value: '有'},
  { key: '自动头灯', value: '有'},
  { key: '自适应远近光', value: '有'},
  { key: '胎压监测', value: '有'},
  { key: '自动驻车', value: '有'},
  { key: '上坡辅助', value: '有'},
  { key: '陡坡缓降', value: '有'},
  { key: '并线辅助', value: '有'},
  { key: '倒车影像', value: '有'},
  { key: '电动后备箱', value: '无'},
]);
const paramDialog = ref(false);
const configParamDialog = ref(false);
const editFlag = ref(false);
const paramIndex = ref(0);
const configeditFlag = ref(false);
const configparamIndex = ref(0);
const btnLoading = ref(false)
const factoryList = ref([])
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

initData();
function initData() {
  carBrandList({exportType: 1})
    .then((res) => {
      carBrand.value = res.records;
    })
    .finally(() => { });
  carTypeList({exportType: 1})
    .then((res) => {
      carType.value = res.records;
    })
    .finally(() => { });
}

function handleTypeChange(val) {
  console.log(val,123)
  carParams.value[0].value = carType.value.filter(o=>o.keyId == val)[0].carTypeName
  carParams.value[0].id = val
}

const dialogImageUrl = ref("");
const imgList = ref([]);

const queryParams = reactive({
  carModelName: "",
  carBrandId: "",
  carTypeId: "",
});
const formParams = ref({
  key: "",
  value: "",
});
const configFormParams = ref({
  key: "",
  value: "有",
});
const dialogVisible = ref(false);

function handleSucess(res, file, fileList) {
  console.log(res,123)
  if (res[0].status == 200) {
    let obj = {};
    obj.name = file.name;
    obj.url = res[0].url;
    imgList.value.push(obj);
  }
}
function cancel() {
  visible.value = false;
}
function handlePictureCardPreview(uploadFile) {
  dialogImageUrl.value = uploadFile.url;
  dialogVisible.value = true;
}
function handleRemove(file, fileList) {
  console.log(file, 123);
  imgList.value.forEach((item, index) => {
    if (item.name == file.name) {
      imgList.value.splice(index, 1);
    }
  });
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
function handleConfigParamsEdit(data) {
  configparamIndex.value = data.$index;
  configFormParams.value = data.row;
  configeditFlag.value = true;
  configParamDialog.value = true;
}
function handleConfigAdd() {
  configFormParams.value = {}
  configeditFlag.value = false;
  configParamDialog.value = true;
}
function handleConfigAddParam() {
  proxy.$refs.configform.validate((valid) => {
    if (!valid) {
      return;
    }
    let obj = { key: configFormParams.value.key, value: configFormParams.value.value };
    if (!configeditFlag.value) {
      configParams.value.push(obj);
    } else {
      configParams.value.splice(configparamIndex.value, 1, obj);
    }
    proxy.$refs.configform.resetFields();
    configParamDialog.value = false;
  });
}

function handleBaseParamsDel(data) {
  carParams.value.splice(data.$index, 1);
  console.log(data);
}
function handleConfigParamsDel(data) {
  configParams.value.splice(data.$index, 1);
}
function handleAddParam() {
  proxy.$refs.form.validate((valid) => {
    if (!valid) {
      return;
    }
    let obj = { key: formParams.value.key, value: formParams.value.value };
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
    if (imgList.value.length == 0) {
      ElMessage({
        type: "error",
        message: "请上传车辆照片",
      });
      return;
    }
    if (carParams.value.length == 0) {
      ElMessage({
        type: "error",
        message: "请添加基本参数",
      });
      return;
    }
    if (configParams.value.length == 0) {
      ElMessage({
        type: "error",
        message: "请添加配置参数",
      });
      return;
    }
    // form.carIdList = selectedCar.value.map((o) => o.carId);
    // console.log(form);
    for(let key in carParams.value) {
      let obj = carParams.value[key]
      console.log(obj,819)
      if(obj.type == 2) {
        obj.id = carType.value.filter(o=>o.carTypeName == obj.value)[0].keyId
      } else if(obj.type == 3){
        obj.id = energys.value.filter(o=>o.label == obj.value)[0].value
      }
    }
    console.log(carParams.value, 12345);

    queryParams.carParams = carParams.value;
    queryParams.configParams = configParams.value;
    queryParams.carPics = imgList.value.map((o) => o.url).join(",");
    console.log(queryParams, 555);
    btnLoading.value = true;
    addOrUptCarModel(queryParams)
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
