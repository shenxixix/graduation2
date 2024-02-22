<template>
  <div>
    <Dialog v-model="visible" title="报价" :padding="0">
      <div class="bg-gray-50 con" v-loading="loading">
        <div class="content">
          <div class="rent-info">
            <div class="fn600 cl3">报价截止时间</div>
            <div v-show="formData.pickUpCarTime" class="mt-4">{{ formatDate(formData.pickUpCarTime)}}</div>
            <div class="cl9 timetips mt-2">企业需在报价截止时间内完成报价，未完成报价的视为放弃本次报价</div>
          </div>
          <div class="rent-info mt-2">
            <div class="fn600 cl3">租赁信息</div>
            <div class="bg-gray-50 rent-box">
              <div class="rent-box-item">
                <div>取车时间</div>
                <div class="mt-4">{{ formData.pickUpCarTime && formatTime(formData.pickUpCarTime) }}</div>
              </div>
              <div class="line">
                <div>{{ formData.dayNum }}天</div>
                <div class="line-con"></div>
                <div>{{ formData.driveType == 1 ? '自驾' : '配司机' }}</div>
              </div>
              <div class="rent-box-item">
                <div>还车时间</div>
                <div class="mt-4">{{ formData.backCarTime && formatTime(formData.backCarTime) }}</div>
              </div>
            </div>
            <div class="mt-4"><span class="cl9">用车备注：</span>{{ formData.remark || '-' }}</div>
            <div class="mt-2"><span class="cl9">取车点：</span>{{ formData.startAddress || '-' }}</div>
            <div class="mt-2"><span class="cl9">还车点：</span>{{ formData.endAddress || '-' }}</div>
            <div class="mt-2"><span class="cl9">联系人：</span>{{ formData.userNickName || '-' }}</div>
            <div class="mt-2"><span class="cl9">联系人手机：</span>{{ formData.userMobile || '-' }}</div>
            <div class="mt-2"><span class="cl9">用车单位：</span>{{ formData.orgName || '-' }}</div>
          </div>
          <div>
            <el-form :model="formData" ref="queryRef" label-position="top">
              <div class="rent-info mt-4" v-for="(item, index) in formData.orgQuotedList" :key="index">
                <div class="fn600">所需车型{{ index + 1 }}</div>
                <div class="carinfo flex">
                  <div class="logo"><img :src="item.carPic"></div>
                  <div>
                    <div class="name">{{ item.carModelName }}（{{ item.carNum }}辆）</div>
                    <div class="type">{{ item.carDesc || '--' }}</div>
                  </div>
                </div>
                <div class="bg-white rounded leading-10">
                  <div class="mile mt-4">
                    <el-form-item label="里程限制(公里)" :prop="`orgQuotedList.${index}.limitMileType`" :rules="[
                      { required: true, message: '不能为空', trigger: 'change' },
                    ]">
                      <el-select v-model="item.limitMileType" placeholder="请选择" style="width: 180px"
                        @change="(val) => handleChange(val, 'limitMileSingle', index)">
                        <el-option label="租期内无里程限制" :value="1" />
                        <el-option label="租期内有里程限制" :value="2" />
                      </el-select>
                    </el-form-item>
                    <div class="item">
                      <el-form-item label=" " :prop="`orgQuotedList.${index}.limitMileSingle`" class="mile-con" :rules="[
                        { required: item.limitMileType == 1 ? false : true, message: '不能为空', trigger: 'change' },
                      ]">
                        <el-input v-model="item.limitMileSingle" clearable onkeyup="this.value=this.value.replace(/\D|^0/g,'')" 
                          :disabled="item.limitMileType == 1 ? true : false" />
                      </el-form-item>
                    </div>
                  </div>
                  <div class="mile mt-4">
                    <el-form-item label="超公里(元/公里)" :prop="`orgQuotedList.${index}.beyondMileFeeType`" :rules="[
                      { required: true, message: '不能为空', trigger: 'change' },
                    ]">
                      <el-select v-model="item.beyondMileFeeType" placeholder="请选择" style="width: 180px" :disabled="item.limitMileType==1"
                        @change="(val) => handleChange(val, 'beyondMileFee', index)">
                        <el-option label="不收费" :value="0" />
                        <el-option label="需收费" :value="2" />
                      </el-select>
                    </el-form-item>
                    <div class="item">
                      <el-form-item label=" " :prop="`orgQuotedList.${index}.beyondMileFee`" class="mile-con" :rules="[
                        { required: true, message: '不能为空', trigger: 'blur' },
                      ]">
                        <el-input v-model="item.beyondMileFee" clearable onkeyup="this.value=this.value.replace(/\D|^0/g,'')" 
                          :disabled="(item.beyondMileFeeType == 0) ? true : false" />
                      </el-form-item>
                    </div>
                  </div>
                  <div class="mile mt-4">
                    <el-form-item label="超时长(元/小时)" :prop="`orgQuotedList.${index}.beyondTimeFeeType`" :rules="[
                      { required: true, message: '不能为空', trigger: 'change' },
                    ]">
                      <el-select v-model="item.beyondTimeFeeType" placeholder="请选择" style="width: 180px"
                        @change="(val) => handleChange(val, 'beyondTimeFee', index)">
                        <el-option label="不收费" :value="0" />
                        <el-option label="需收费" :value="2" />
                      </el-select>
                    </el-form-item>
                    <div class="item">
                      <el-form-item label=" " :prop="`orgQuotedList.${index}.beyondTimeFee`" class="mile-con" :rules="[
                        { required: true, message: '不能为空', trigger: 'blur' },
                      ]">
                        <el-input v-model="item.beyondTimeFee" clearable onkeyup="this.value=this.value.replace(/\D|^0/g,'')"
                          :disabled="item.beyondTimeFeeType == 0 ? true : false" />
                      </el-form-item>
                    </div>
                  </div>
                  <div>
                    <div>调派车辆</div>
                    <div class="bg-gray-50 car-box" v-for="(e, idx) in item.carFeeList" :key="idx">
                      <div>车辆{{ idx + 1 }}</div>
                      <el-form-item label="拟派车辆车龄" :prop="`orgQuotedList.${index}.carFeeList.${idx}.carAge`" :rules="[
                        { required: true, message: '不能为空', trigger: 'change' },
                      ]">
                        <el-select v-model="e.carAge"  style="width: 100%;" >
                          <el-option v-for="item in carAge" :key="item.value" :label="item.key"
                            :value="item.value" />
                        </el-select>
                      </el-form-item>
                      <el-form-item label="租车费(元)" :prop="`orgQuotedList.${index}.carFeeList.${idx}.singleRentFee`"
                        :rules="[
                          { required: true, message: '不能为空', trigger: 'blur' },
                        ]">
                        <el-input v-model="e.singleRentFee" clearable />
                      </el-form-item>
                      <div class="mile mt-4" v-if="formData.driveType!=1">
                          <el-form-item label="驾驶员费(元)"
                            :prop="`orgQuotedList.${index}.carFeeList.${idx}.singleDriverFeeType`" :rules="[
                              { required: true, message: '不能为空', trigger: 'change' },
                            ]">
                            <el-select v-model="e.singleDriverFeeType" placeholder="请选择" style="width: 180px"
                              @change="(val) => handleListChange(val, 'singleDriverFee', index, idx)">
                              <el-option label="不收费" :value="0" />
                              <el-option label="需收费" :value="2" />
                            </el-select>
                          </el-form-item>
                          <div class="item">
                            <el-form-item label=" " :prop="`orgQuotedList.${index}.carFeeList.${idx}.singleDriverFee`"
                              class="mile-con" :rules="[
                                { required: true, message: '不能为空', trigger: 'blur' },
                              ]">
                              <el-input v-model="e.singleDriverFee" clearable
                                :disabled="e.singleDriverFeeType == 0 ? true : false" />
                            </el-form-item>
                          </div>
                        </div>
                    </div>
                  </div>
                </div>
                <div class="total">费用合计：{{ getPrice(index) }}元</div>
                <div class="tips">此报价不含超里程、超时长、过路费、住宿费、停车费等费用，若产生了相应费用，用车结束时由驾驶员上传。</div>
              </div>
            </el-form>
          </div>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleCancel">取消报价</el-button>
          <el-button type="primary" @click="confirm">
            报价
          </el-button>
        </span>
      </template>
    </Dialog>
    <!-- <Confirm v-model="confirmVisible" v-if="confirmVisible" :data="formData"></Confirm> -->
    <el-dialog v-model="confirmVisible" title="二次确认报价" width="750" padding="0" :before-close="handleClose">
      <div class="bg-gray-50 pop">
        <div class="desc">请再次确认报价信息，提交后不可修改</div>
        <div class="car" v-for="(item, index) in formData.orgQuotedList" :key="index">
          <div class="carinfo flex">
            <div class="logo"><img :src="item.carPic"></div>
            <div>
              <div class="name">{{ item.carModelName }}（{{ item.carNum }}辆）</div>
              <div class="type">{{ item.carDesc || '--' }}</div>
            </div>
          </div>
          <div class="fs12 mt-4">{{ item.limitMileType == 1 ? '租期内无里程限制' : `租期内有里程限制，共计${item.limitMileSingle}公里` }}</div>
          <div class="fs12 mt-2">超公里{{ item.beyondMileFee }}元/公里，超小时{{ item.beyondTimeFee }}元/小时</div>
          <div class="line bg-gray-50"></div>
          <div class="price" v-for="(e,index1) in item.carFeeList" :key="index1">
            <div class="mt-4 fn600">车{{index1+1}}: {{ e.carAge }}年车龄</div>
            <div class="price-item">
              <div>租车费</div>
              <div class="fn600">¥{{ e.singleRentFee }}</div>
            </div>
            <div class="price-item" v-if="e.driverType!=1">
              <div>驾驶员费用</div>
              <div class="fn600">¥{{ e.singleDriverFee }}</div>
            </div>
            <div class="price-item price-item-total">
              <div class="fn600"><span class="cl9 mr-2 fn400">小计</span>¥{{ Number(e.singleRentFee) +
                Number(e.singleDriverFee) }}</div>
            </div>
            <div class="line bg-gray-50"></div>
          </div>
          <div class="line bg-gray-50"></div>
          <div class="total-price">
            费用合计 <span>¥{{ getPrice(index) }}</span>
          </div>
          <div class="tips">此报价不含超里程、超时长、过路费、住宿费、停车费等费用，若产生了相应费用，用车结束时由驾驶员上传。</div>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="confirmVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="confirmLoading">
            报价
          </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="cancelVisible" title="取消报价" width="550" padding="0">
      <div class="pop">
        <el-form ref="ruleFormRef" :model="params" class="demo-ruleForm">
          <el-form-item label="取消原因" prop="cancelRemark" :rules="[
            { required: true, message: '请选择', trigger: 'change' },
          ]">
            <el-select v-model="params.cancelRemark" style="width:100%">
              <el-option label="暂无空闲车辆" value="1" />
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelVisible = false">取消</el-button>
          <el-button type="primary" @click="handleCancelQuote" :loading="loading">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { upQuotedDetail, handleQuoted, cancelQuoted } from "@/api/deal";
import { fixCarList } from "@/api/car"
import { fixDriverList } from "@/api/driver"
import { reactive, watch } from "vue";
import Confirm from "./confirm.vue"

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  id: {
    type: String,
    default: "",
  },
});
const formData = ref({
});
const params = ref({})
const visible = ref(false);
const loading = ref(false)
const confirmVisible = ref(false);
const selLoading = ref(false)
const options = ref([])

const carAge = ref([
  {key:'1年车龄',value:1},
  {key:'2年车龄',value:2},
  {key:'3年车龄',value:3},
  {key:'4年车龄',value:4},
  {key:'5年车龄',value:5},
  {key:'6年车龄',value:6},
  {key:'7年车龄',value:7},
  {key:'8年车龄',value:8},
  {key:'9年车龄',value:9},
  {key:'10年车龄',value:10}
])

watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
    init()
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
  () => props.id,
  (val) => {
    if (val) {
      getData();
    }
  },
  { immediate: true }
);

const carList = ref([])
const driverList = ref([])

function formatDate(d=new Date()) {
  let date = new Date(d)
  let year = date.getFullYear();
  let month = date.getMonth() + 1;
  let day = date.getDate();
  let hours = date.getHours();
  let minutes = date.getMinutes();

  let arr = ['日', '一', '二', '三', '四', '五', '六']
 
  month = month < 10 ? "0" + month : month;
  day = day < 10 ? "0" + day : day;
  hours = hours < 10 ? "0" + hours : hours;
  minutes = minutes < 10 ? "0" + minutes : minutes;
 
  return `${year}年${month}月${day}日 周${arr[date.getDay()]} ${hours}:${minutes}`;
}
function clic(id) {
  console.log(123)
  remoteMethod('1', id)
}

function init() {
  fixCarList({ exportType: 1 }).then((res) => {
    //carList.value = res.records
  });
  fixDriverList({ exportType: 1 }).then((res) => {
    driverList.value = res.records
  });
}

async function queryCarList(id, index) {
  let arr = await fixCarList({ exportType: 1, carModelId: id, quotedQuery: 1 })
  console.log(arr,181)
  formData.value.orgQuotedList[index].carList = arr.records
}

function remoteMethod(query, id) {
  console.log(query, id, 99999)
  if (query) {
    selLoading.value = true
    fixCarList({ exportType: 1, carModelId: id, quotedQuery: 1 }).then((res) => {
      carList.value = res.records
    }).finally(() => { selLoading.value = false });
  } else {
    //carList.value = []
  }
}

function getData() {
  upQuotedDetail({ keyId: props.id }).then((res) => {
    formData.value = res
    for (let key in formData.value.orgQuotedList) {
      formData.value.orgQuotedList[key].beyondMileFeeType = 0
      formData.value.orgQuotedList[key].beyondTimeFeeType = 0
      queryCarList(formData.value.orgQuotedList[key].carModelId, key)
      for (let ele in formData.value.orgQuotedList[key].carFeeList) {
        formData.value.orgQuotedList[key].carFeeList[ele].singleDriverFeeType = 0
        formData.value.orgQuotedList[key].carFeeList[ele].singleRentFee = ''
      }
    }
  });
}

function getPrice(i) {
  let p = 0
  formData.value.orgQuotedList[i].carFeeList.forEach(e => {
    p += (Number(e.singleDriverFee) + Number(e.singleRentFee))
  })
  return p
}

function handleChange(val, type, index) {
  if (val <= 1) {
    formData.value.orgQuotedList[index][type] = 0
    if(type == 'limitMileSingle') {
      formData.value.orgQuotedList[index].beyondMileFeeType = 0
      formData.value.orgQuotedList[index].beyondMileFee = 0
    }
  }
  
}
function handleListChange(val, type, index, idx) {
  if (val <= 1) {
    formData.value.orgQuotedList[index].carFeeList[idx][type] = 0
  }
}
const cancelVisible = ref(false)
function handleCancelQuote() {
  proxy.$refs.ruleFormRef.validate((valid) => {
    if (!valid) {
      return;
    }
    loading.value = true
    cancelQuoted({ quotationId: formData.value.quotationId, notQuotedReason: params.value.notQuotedReason })
      .then(() => {
        proxy.$message.success("操作成功");
        visible.value = false;
        emit("update");
      })
      .finally(() => {
        loading.value = false
      });
  });

}

function handleCancel() {
  cancelVisible.value = true
}
// 提交表单
const confirmLoading = ref(false);
function confirm() {
  proxy.$refs.queryRef.validate((valid) => {
    if (!valid) {
      return;
    }
    confirmVisible.value = true
  });
}

function handleSubmit() {
  confirmLoading.value = true;

  let obj = JSON.parse(JSON.stringify(formData.value))
  for (let key in obj.orgQuotedList) {
    obj.orgQuotedList[key].beyondMileFee *= 100
    obj.orgQuotedList[key].beyondTimeFee *= 100
    for (let ele in obj.orgQuotedList[key].carFeeList) {
      obj.orgQuotedList[key].carFeeList[ele].singleDriverFee *= 100
      obj.orgQuotedList[key].carFeeList[ele].singleRentFee *= 100
    }
  }
  console.log(obj, 123)
  handleQuoted(obj)
    .then(() => {
      proxy.$message.success("操作成功");
      visible.value = false;
      emit("update");
    })
    .finally(() => {
      confirmLoading.value = false;
    });
}
</script>

<style lang="scss" scoped>
.item :deep(.el-form-item__label:before) {
  content: '' !important;
}

.item {
  flex: 1
}
.timetips {
  font-size: 12px;
}

.tips {
  color: #999999;
  margin-top: 15px;
}

.fn600 {
  font-weight: 600;
}

.cl3 {
  color: #333333;
}

.cl9 {
  color: #999999;
}

:deep(.el-dialog__body) {
  padding: 0;
}

.con {
  position: relative;
}

.content {
  padding: 10px;
}

.rent-info {
  background: #fff;
  padding: 15px;

  .total {
    font-size: 18px;
    font-weight: 600;
    margin-top: 20px;
  }

  .rent-box {
    display: flex;
    align-items: center;
    padding: 10px;
    border-radius: 10px;
    margin-top: 10px;

    .rent-box-item {
      width: 20%;
      text-align: center;
    }

    .line {
      flex: 1;
      text-align: center;

      .line-con {
        height: 2px;
        background: #f3f3f3;
        margin: 5px 0;
      }
    }
  }

  .carinfo {
    display: flex;
    align-items: center;
    margin-top: 15px;

    .logo {
      width: 88px;
      height: 55px;
      margin-right: 10px;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .name {
      font-size: 18px;
      font-weight: 600;
    }

    .type {
      font-size: 12px;
      color: #AAAAAA;
      margin-top: 10px;
    }
  }

  .mile {
    display: flex;
    align-items: flex-end;
  }

  .mile-con {
    margin-left: 20px;
    flex: 1;
  }

  .car-box {
    padding: 0 10px 10px;
    border-radius: 10px;
  }
}

.pop {
  padding: 20px;

  .desc {
    color: rgb(251, 145, 17);
    background: #fff;
    text-align: center;
    height: 74px;
    line-height: 74px;
    font-size: 16px;
  }

  .car {
    padding: 20px;
    background: #fff;
    margin-top: 10px;
  }

  .carinfo {
    display: flex;
    align-items: center;
    margin-top: 15px;

    .logo {
      width: 88px;
      height: 55px;
      margin-right: 10px;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .name {
      font-size: 18px;
      font-weight: 600;
    }

    .type {
      font-size: 12px;
      color: #AAAAAA;
      margin-top: 10px;
    }
  }

  .price {}

  .price-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 10px;
  }

  .price-item-total {
    justify-content: flex-end;
  }

  .total-price {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
    font-size: 18px;

    span {
      color: rgb(251, 145, 17);
      margin-left: 10px;
    }
  }
}
</style>
