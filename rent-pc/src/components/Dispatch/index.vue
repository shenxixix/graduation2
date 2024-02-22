<template>
  <div>
    <Dialog v-model="visible" title="派车" :padding="0">
      <div class="bg-gray-50 con" v-loading="loading">
        <div class="content">
          <div class="rent-info">
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
                <!-- <div class="fn600">所需车型{{ index + 1 }}</div> -->
                <div class="carinfo flex">
                  <div class="logo"><img :src="item.carPic"></div>
                  <div>
                    <div class="name">{{ item.carModelName }}<span v-if="type != 1">（{{ item.carNum }}辆）</span></div>
                    <div class="type">{{ item.carDesc || '--' }}</div>
                  </div>
                </div>
                <div class="fs12 mt-4">{{ item.limitMileType == 1 ? '租期内无里程限制' : `租期内有里程限制，共计${item.limitMileSingle ||
                  0}公里` }}</div>
                <div class="fs12 mt-2">超公里{{ item.beyondMileFee / 100 }}元/公里，超小时{{ item.beyondTimeFee / 100 }}元/小时</div>
                <div class="bg-white rounded leading-10">
                  <div>
                    <div class="cl3 fn600">调派车辆</div>
                    <div class="bg-gray-50 car-box" v-for="(e, idx) in item.carFeeList" :key="e.carId">
                      <div>车{{ idx + 1 }}:{{ e.carAge }}年车龄</div>
                      <div class="flex-bet">
                        <div>租车费</div>
                        <div>￥{{ e.singleRentFee/100 }}</div>
                      </div>
                      <div class="flex-bet mt-2">
                        <div>驾驶员费用</div>
                        <div>￥{{ e.singleDriverFee/100 }}</div>
                      </div>
                      <div class="price-item price-item-total mb-4">
                        <div><span class="cl9 mr-2">小计</span>¥{{ (Number(e.singleRentFee) +
                          Number(e.singleDriverFee))/100 }}</div>
                      </div>
                      <el-form-item label="车牌号" :prop="`orgQuotedList.${index}.carFeeList.${idx}.carId`" :rules="[
                        { required: true, message: '不能为空', trigger: 'change' },
                      ]">

                        <el-select v-model="e.carId" :loading="selLoading" style="width: 100%;"
                          @change="(val) => handleCarChange(val, index, idx)">
                          <el-option v-for="item in item.carList" :key="item.carId" :label="item.license"
                            :value="item.carId" />
                        </el-select>
                      </el-form-item>
                      <template v-if="formData.driveType == 2">
                        <el-form-item label="驾驶员" :prop="`orgQuotedList.${index}.carFeeList.${idx}.driverId`" :rules="[
                          { required: true, message: '不能为空', trigger: 'change' },
                        ]">
                          <el-select v-model="e.driverId" placeholder="请选择" style="width: 100%">
                            <el-option v-for="dict in driverList" :key="dict.keyId" :label="dict.userNickName"
                              :value="dict.keyId" />
                          </el-select>
                        </el-form-item>
                      </template>
                      <template v-if="formData.driveType == 1">
                        <el-form-item label="送取车人" :prop="`orgQuotedList.${index}.carFeeList.${idx}.driverId`" :rules="[
                          { required: true, message: '不能为空', trigger: 'change' },
                        ]">
                          <el-select v-model="e.driverId" placeholder="请选择" style="width: 100%">
                            <el-option v-for="dict in driverList" :key="dict.keyId" :label="dict.userNickName"
                              :value="dict.keyId" />
                          </el-select>
                        </el-form-item>
                      </template>
                    </div>
                  </div>
                </div>
                <!-- <div class="total">费用合计：{{ getPrice(index) }}元</div> -->
              </div>
            </el-form>
          </div>
        </div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleCancel">取消</el-button>
          <el-button type="primary" @click="confirm" :loading="confirmLoading">
            确定派车
          </el-button>
        </span>
      </template>
    </Dialog>
  </div>
</template>

<script setup>
import { upDispatchDetail, handleDispatch } from "@/api/deal";
import { fixCarList } from "@/api/car"
import { fixDriverList } from "@/api/driver"
import { reactive, watch } from "vue";

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
  console.log(arr, 181)
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

function handleCarChange(val, i, idx) {
  console.log(val, i, idx, 99)
  formData.value.orgQuotedList[i].carFeeList[idx].license = formData.value.orgQuotedList[i].carList.filter(o => o.carId == formData.value.orgQuotedList[i].carFeeList[idx].carId)[0].license
}

function getData() {
  upDispatchDetail({ keyId: props.id }).then((res) => {
    formData.value = res
    for (let key in formData.value.orgQuotedList) {
      formData.value.orgQuotedList[key].beyondMileFeeType = 0
      formData.value.orgQuotedList[key].beyondTimeFeeType = 0
      queryCarList(formData.value.orgQuotedList[key].carModelId, key)
      // formData.value.orgQuotedList[key].carList = queryCarList(formData.value.orgQuotedList[key].carModelId)
      console.log(formData.value.orgQuotedList[key], 90000001)
      for (let ele in formData.value.orgQuotedList[key].carFeeList) {
        formData.value.orgQuotedList[key].carFeeList[ele].carId = ''
        formData.value.orgQuotedList[key].carFeeList[ele].driverId = ''
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
    if (type == 'limitMileSingle') {
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


function handleCancel() {
  visible.value = false;
}
// 提交表单
const confirmLoading = ref(false);
function confirm() {
  proxy.$refs.queryRef.validate((valid) => {
    if (!valid) {
      return;
    }
    handleSubmit()
  });
}

function handleSubmit() {
  confirmLoading.value = true;

  let obj = JSON.parse(JSON.stringify(formData.value))
  obj.type = 1
  // for (let key in obj.orgQuotedList) {
  //   console.log(obj.orgQuotedList[key], 9001)
  //   obj.orgQuotedList[key].beyondMileFee *= 100
  //   obj.orgQuotedList[key].beyondTimeFee *= 100
  //   for (let ele in obj.orgQuotedList[key].carFeeList) {
  //     obj.orgQuotedList[key].carFeeList[ele].singleDriverFee *= 100
  //     obj.orgQuotedList[key].carFeeList[ele].singleRentFee *= 100
  //     // obj.orgQuotedList[key].carFeeList[ele].license = obj.orgQuotedList[key].carList.filter(o => o.carId == obj.orgQuotedList[key].carFeeList[ele].carId)[0].license
  //   }
  // }
  // console.log(obj, 123)
  handleDispatch(obj)
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

.flex-bet {
  display: flex;
  justify-content: space-between;
  line-height: 20px;
}

.price-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 10px;
  }

  .price-item-total {
    justify-content: flex-end;
    line-height: 20px;
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
