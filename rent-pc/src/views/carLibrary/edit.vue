<template>
  <div>
    <Dialog v-model="visible" title="编辑车辆型号" :footerShow="false" :closeShow="true" :padding="0">
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
              <el-select v-model="queryParams.carBrandId" placeholder="请选择" size="large" filterable >
                <el-option v-for="dict in carBrand" :key="dict.keyId" :label="dict.carBrandName" :value="dict.keyId" />
              </el-select>
            </el-form-item>
            <el-form-item label="车辆类型" :required="true" prop="carTypeId" :rules="[
              { required: true, message: '请选择', trigger: 'change' },
            ]">
              <el-select v-model="queryParams.carTypeId" placeholder="请选择" size="large" @change="handleTypeChange">
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
              <el-table ref="singleTableRef" :data="queryParams.carParams" style="width: 100%; margin-top: 20px" border>
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
            <!-- <el-form-item label="基本参数" :required="true">
              <el-table ref="singleTableRef" :data="queryParams.carParams" style="width: 100%; margin-top: 20px" border>
                <el-table-column label="参数项" min-width="150" align="center" prop="key" />
                <el-table-column label="数值" min-width="150" align="center" prop="value" />
              </el-table>
            </el-form-item> -->
            <el-form-item label="配置参数" :required="true">
              <el-table ref="singleTableRef" :data="queryParams.configParams" style="width: 100%; margin-top: 20px"
                border>
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
              <el-dialog v-model="configParamDialog" style="padding-top:30px">
                <el-form :model="configFormParams" label-width="120px" label-position="top" ref="configform">
                  <div class="con">
                    <el-form-item label="参数项" :required="true" prop="key" :rules="[
                      { required: true, message: '请输入', trigger: 'blur' },
                    ]">
                      <el-input v-model="configFormParams.key" />
                    </el-form-item>
                    <el-form-item label="数值" :required="true" prop="value">
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
import { carBrandList, carTypeList, addOrUptCarModel, viewCarModel } from "@/api/car";
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
const { energys } = proxy.dict(["energys"]);
const visible = ref(false);
const carBrand = ref([]);
const carType = ref([]);
const fileList = ref([]);
const paramDialog = ref(false);
const configParamDialog = ref(false)
const queryParams = ref({
});
const btnLoading = ref(false)
watch(() => props.modelValue, val => {
  visible.value = val;
  if (props.keyId) {
    getInfo();
  }
}, { immediate: true });
watch(
  () => visible.value,
  (val) => {
    emit("update:modelValue", val);
  }
);

initData();
function initData() {
  carBrandList({ exportType: 1 })
    .then((res) => {
      carBrand.value = res.records;
    })
    .finally(() => { });
  carTypeList({ exportType: 1 })
    .then((res) => {
      carType.value = res.records;
    })
    .finally(() => { });
}
function getInfo() {
  viewCarModel({ keyId: props.keyId })
    .then((res) => {
      console.log(res, 1233333)
      queryParams.value = res
      fileList.value = res.carPics.map(o => ({
        url: o
      }))
      imgList.value = res.carPics.map(o => ({
        url: o
      }))
      console.log(queryParams)
    })
    .finally(() => { });
}

function handleTypeChange(val) {
  console.log(val,123)
  queryParams.value.carParams[0].value = carType.value.filter(o=>o.keyId == val)[0].carTypeName
  queryParams.value.carParams[0].id = val
}

const dialogImageUrl = ref("");
const imgList = ref([]);

const formParams = ref({
  key: "",
  value: "",
});
const configFormParams = ref({
  key: "",
  value: ""
})
const dialogVisible = ref(false);


const editFlag = ref(false);
const paramIndex = ref(0);
const configeditFlag = ref(false);
const configparamIndex = ref(0);

function handleConfigParamsDel(data) {
  queryParams.value.configParams.splice(data.$index, 1);
}

function handleConfigParamsEdit(data) {
  configparamIndex.value = data.$index;
  configFormParams.value = data.row;
  configeditFlag.value = true;
  configParamDialog.value = true;
}

function handleSucess(res, file, fileList) {
  if (res[0].status == 200) {
    let obj = {};
    obj.name = file.name;
    obj.url = res[0].url;
    imgList.value.push(obj);
    console.log(imgList.value)
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
    if (item.url == file.url) {
      imgList.value.splice(index, 1);
    }
  });
}

function handleConfigAdd() {
  configFormParams.value = {}
  configeditFlag.value = false;
  configParamDialog.value = true
}
function handleConfigAddParam() {
  proxy.$refs.configform.validate((valid) => {
    if (!valid) {
      return;
    }
    console.log(configFormParams)
    let obj = { key: configFormParams.value.key, value: configFormParams.value.value }
    if (!configeditFlag.value) {
      queryParams.value.configParams.push(obj)
    } else {
      queryParams.value.configParams.splice(configparamIndex.value, 1, obj);
    }
    proxy.$refs.configform.resetFields()
    configParamDialog.value = false;
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
    if (queryParams.value.carParams.length == 0) {
      ElMessage({
        type: "error",
        message: "请添加基本参数",
      });
      return;
    }
    if (queryParams.value.configParams.length == 0) {
      ElMessage({
        type: "error",
        message: "请添加配置参数",
      });
      return;
    }
    for(let key in queryParams.value.carParams) {
      let obj = queryParams.value.carParams[key]
      console.log(obj,819)
      if(obj.type == 2) {
        obj.id = carType.value.filter(o=>o.carTypeName == obj.value)[0].keyId
      } else if(obj.type == 3){
        obj.id = energys.value.filter(o=>o.label == obj.value)[0].value
      }
    }
    // form.carIdList = selectedCar.value.map((o) => o.carId);
    queryParams.value.carPics = imgList.value.map(o => o.url).join(',')
    console.log(queryParams, 989);
    btnLoading.value = true;
    addOrUptCarModel(queryParams.value)
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
