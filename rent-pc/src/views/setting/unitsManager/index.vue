<template>
    <div class="p-4 bg-gray-50 min-h-full flex flex-col">
        <div class="formcon" v-loading="loading">
            <el-form :model="formData" ref="queryRef" label-position="top">
                <div class="p-4 bg-white rounded leading-10 w60">
                    <el-form-item label="企业名称" prop="epName"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.epName" placeholder="请输入" disabled />
                    </el-form-item>
                    <el-form-item label="社会信用代码" prop="creditCode"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.creditCode" placeholder="请输入" disabled />
                    </el-form-item>
                    <el-form-item label="联系人" prop="contacts"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.contacts" placeholder="请输入" clearable />
                    </el-form-item>
                    <el-form-item label="联系电话" prop="contactsPhone"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.contactsPhone" placeholder="请输入" clearable />
                    </el-form-item>
                    <el-form-item label="登录账号" prop="loginAccount"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.loginAccount" placeholder="请输入" disabled />
                    </el-form-item>
                    <div class="cl9">请前往“账户管理”进行修改</div>
                    <el-form-item label="营业时间" prop="openingTime"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.openingTime" placeholder="请输入" clearable />
                    </el-form-item>
                    <el-form-item label="所在地区" prop="areaCode"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-select v-model="formData.areaCode" style="width: 100%;">
                          <el-option v-for="item in area" :key="item.value" :label="item.name"
                            :value="item.value" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="详细地址" prop="addressd"
                        :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <SelectAddress v-model="formData.addressd">
                        <el-input v-model="formData.addressd.name" placeholder="请地图中选择位置" readonly> </el-input>
                        </SelectAddress>
                    </el-form-item>
                    <el-form-item label="企业简介" prop="introduce" :rules="[{ required: true, message: '不能为空', trigger: 'blur' }]">
                        <el-input v-model="formData.introduce" :rows="4" type="textarea" placeholder="请输入" />
                    </el-form-item>
                    <el-form-item label="服务起止日期" prop="serviceTime"
                        :rules="[{ required: true, message: '不能为空', trigger: 'change' }]">
                        <el-date-picker v-model="formData.serviceTime" format="YYYY/MM/DD"
        value-format="x" type="daterange" range-separator="至" start-placeholder="开始时间"
                            end-placeholder="结束时间" disabled/>
                    </el-form-item>
                    <el-form-item label="门头照片" :required="true">
                        <el-upload v-model:file-list="fileList" list-type="picture-card"
                            :on-preview="handlePictureCardPreview" :on-remove="handleRemove" :auto-upload="true"
                            :limit="1"
                            :on-success="handleSucess" action="/fileserver/upload" multiple>
                            <el-icon>
                                <Plus />
                            </el-icon>
                        </el-upload>

                        <el-dialog v-model="dialogVisible">
                            <img w-full :src="dialogImageUrl" alt="Preview Image" />
                        </el-dialog>
                    </el-form-item>
                    <el-form-item label="承诺书" prop="promisePdf">
                        <el-upload v-model:file-list="promisefileList" action="/fileserver/upload" ref="uploadRef" :on-change="handleFileChange"
                            :before-upload="beforeUpload" :on-success="onPromoseSuccess" style="width: 100%;">
                            <el-button type="primary">点击上传</el-button>
                            <template #tip>
                                <div class="el-upload__tip">只能上传PDF文件</div>
                            </template>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="价目表" prop="priceListPdf">
                        <el-upload v-model:file-list="pricefileList" action="/fileserver/upload" :on-change="handlePriceFileChange"
                            :before-upload="beforeUpload" :on-success="onPriceSuccess" :on-remove="onRemove" style="width: 100%;">
                            <el-button type="primary">点击上传</el-button>
                            <template #tip>
                                <div class="el-upload__tip">只能上传PDF文件</div>
                            </template>
                        </el-upload>
                    </el-form-item>
                </div>
            </el-form>
            <el-button type="primary" size="large" style="width: 150px;" @click="handleSubmit" :loading="confirmLoading">更新资料</el-button>
        </div>
    </div>
</template>

<script setup>
import { fixedEpDetail, updateFixedEp } from '@/api/deal';
import { nextTick } from 'vue';

const { proxy } = getCurrentInstance();


const area = ref([
    {name:'万州区',value:101},
    {name:'涪陵区',value:102},
    {name:'渝中区 ',value:103},
    {name:'大渡口区',value:104},
    {name:'江北区',value:105},
    {name:'沙坪坝区',value:106},
    {name:'九龙坡区',value:107},
    {name:'南岸区',value:108},
    {name:'北碚区',value:109},
    {name:'万盛区',value:110},
    {name:'双桥区',value:111},
    {name:'渝北区',value:112},
    {name:'巴南区',value:113},
    {name:'黔江区',value:114},
    {name:'长寿区',value:115},
    {name:'两江新区',value:116},
    {name:'万盛经开区',value:117},
    {name:'高新区',value:118},
    {name:'大足区',value:225},
    {name:'荣昌区',value:226},
    {name:'潼南区',value:223},
    {name:'城口县',value:229},
    {name:'丰都县',value:230},
    {name:'垫江县',value:231},
    {name:'忠县',value:233},
    {name:'云阳县',value:235},
    {name:'奉节县',value:236},
    {name:'巫山县',value:237},
    {name:'巫溪县',value:238},
    {name:'石柱土家族自治县',value:240},
    {name:'秀山土家族苗族自治县',value:241},
    {name:'酉阳土家族苗族自治县',value:242},
    {name:'彭水苗族土家族自治县',value:243},
    {name:'綦江区',value:222},
    {name:'铜梁区',value:224},
    {name:'璧山区',value:227},
    {name:'梁平区',value:228},
    {name:'武隆区',value:232},
    {name:'开州区',value:234}
])


const loading = ref(false);
const promisefileList = ref([]);
const pricefileList = ref([])
const formData = ref({
    addressd: {
        name:''
    }
})
getData();
function getData() {
    loading.value = true;
    fixedEpDetail({}).then(res => {
        console.log(res, 'kkkkres');
        formData.value = res
        formData.value.serviceTime = [res.serviceStartTime,res.serviceEndTime]
        formData.value.addressd = JSON.parse(res.address)
        fileList.value = [{url:res.epPics}]
        imgList.value = [{url:res.epPics}]
        if(res.promisePdf){
            promisefileList.value = [{url:res.promisePdf,name:'承诺书'}]
        }
        if(res.priceListPdf){
            pricefileList.value = [{url:res.priceListPdf,name:'价目表'}]
        }
        
        
    }).finally(() => {
        loading.value = false;
    });
}

const imgList = ref([]);
const fileList = ref([]);
const dialogImageUrl = ref('')
const dialogVisible = ref(false)

function handleSucess(res, file, fileList) {
    console.log(res, 123)
    if (res[0].status == 200) {
        let obj = {};
        obj.name = file.name;
        obj.url = res[0].url;
        imgList.value.push(obj);
    }
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

function handlePriceFileChange(file,files) {
    console.log(files,123)
    if (files.length > 1) {
        pricefileList.value = [files[files.length - 1]]
    } else {
        pricefileList.value = files
    }
}
function handleFileChange(file,files) {
    console.log(files,123)
    if (files.length > 1) {
        promisefileList.value = [files[files.length - 1]]
    } else {
        promisefileList.value = files
    }
}

// 上传

function beforeUpload(file) {
  console.log(file, "fffffile");
  const isPDF = file.type === "application/pdf";
  if (!isPDF) {
    ElMessage({
      message: "文件格式错误.",
      type: "error",
    });
    return false;
  }
}

function onPromoseSuccess(res) {
    console.log(res,999999999)
  formData.value.promisePdf = res[0].url;
}
function onPriceSuccess(res) {
  formData.value.priceListPdf = res[0].url;
}
const confirmLoading = ref(false)
function handleSubmit() {
    console.log(formData.value,888)
    proxy.$refs.queryRef.validate(valid => {
        if (!valid) {
            return;
        }
        console.log(promisefileList.value,9001)
        formData.value.epPics = imgList.value[0].url
        formData.value.address = JSON.stringify(formData.value.addressd)
        confirmLoading.value = true;
        updateFixedEp(formData.value).then(() => {
            proxy.$message.success('操作成功');
            getData()
        }).finally(() => {
            confirmLoading.value = false;
        })
    });
}

</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
    background: $theme;
    color: white;
}

.formcon {
    background: #fff;
    padding: 15px 15px 15px 80px;
}

.w60 {
    width: 50%;
}

.cl9 {
    color: #999999;
    font-size: 12px;
    line-height: 12px;
    margin: -10px 0 10px;
}
</style>
