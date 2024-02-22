<template>
  <div>
    <Dialog v-model="visible" title="结算单明细" :padding="0" :footerShow="false">
      <div class="bg-gray-50 con" v-loading="loading">
        <div class="content">
          <div class="car">
            <div class="carinfo flex">
              <div class="logo"><img :src="data.carPic"></div>
              <div>
                <div class="name">{{ data.carModelName }}</div>
                <div class="type">{{ data.carDesc || '--' }}</div>
              </div>
              <div class="disstatus">{{ formData.dispatchStatus == 2 ? '已结束' : formData.dispatchStatus == 4 ? '已取消' : ''
              }}</div>
            </div>
            <div class="flex-btw">
              <div>车牌号</div>
              <div>{{ data.carFeeList && data.carFeeList[0].license }}</div>
            </div>
            <div class="flex-btw">
              <div>驾驶员</div>
              <div>{{ data.carFeeList && data.carFeeList[0].driverName }}</div>
            </div>
            <template v-if="formData.dispatchStatus == 4">
              <div class="flex-btw">
                <div>取消原因</div>
                <div>{{ formData.cancelReason }}</div>
              </div>
              <div class="flex-btw">
                <div>违约金</div>
                <div>{{ formData.penaltyFee / 100 }}</div>
              </div>
              <div class="flex-btw">
                <div>取消凭证</div>
                <div><el-button type="text" @click="dialogVisible = true" class="change">查看</el-button></div>
                <el-dialog v-model="dialogVisible">
                  <img w-full :src="formData.penaltyFeePic" alt="Preview Image" />
                </el-dialog>
              </div>
            </template>
          </div>
          <div class="rent-info mt-2">
            <div class="fn600 cl3">费用明细</div>
            <div class="status">{{ formData.settlementStatus == 0 ? '待结算' : '已结算' }}</div>
            <div class="fs12 mt-4">{{ data.limitMileType == 1 ? '租期内无里程限制' : `租期内有里程限制，共计${data.limitMileSingle || 0}公里`
            }}</div>
            <div class="fs12 mt-2">超公里{{ data.beyondMileFee / 100 }}元/公里，超小时{{ data.beyondTimeFee / 100 }}元/小时</div>
            <div class="line bg-gray-50"></div>
            <div class="price" v-for="e in data.carFeeList" :key="e.carId">
              <div class="price-item">
                <div>租车费</div>
                <div class="fn600">¥{{ e.singleRentFee && e.singleRentFee / 100 }}</div>
              </div>
              <div class="price-item">
                <div>驾驶员费用</div>
                <div class="fn600">¥{{ e.singleDriverFee && e.singleDriverFee / 100 }}</div>
              </div>
              <template v-if="formData.dispatchStatus == 4">
                <div class="price-item">
                  <div>租车费扣减</div>
                  <div class="fn600">¥{{ e.singleRentFee && e.singleRentFee / 100 * -1 }}</div>
                </div>
                <div class="price-item">
                  <div>驾驶员费用扣减</div>
                  <div class="fn600">¥{{ e.singleDriverFee && e.singleDriverFee / 100 * -1 }}</div>
                </div>
                <div class="price-item">
                  <div>取消用车违约金</div>
                  <div class="fn600">¥{{ formData.penaltyFee / 100 }}</div>
                </div>
              </template>
              <template v-else>
                <div class="price-item" v-if="e.overMile">
                  <div>超里程（{{ e.overMile }}公里）</div>
                  <div class="fn600">¥{{ e.overMileFee / 100 || 0 }}</div>
                </div>
                <div class="price-item" v-if="e.overDuration">
                  <div>超时长（{{ e.overDuration }}小时）</div>
                  <div class="fn600">¥{{ e.overDurationFee / 100 || 0 }}</div>
                </div>
                <div class="price-item">
                  <div>过路费</div>
                  <div class="fn500 flex-center">
                    <el-button type="text" @click="dialogRoadVisible = true" class="change" v-if="formData.roadFeePic">凭证</el-button>
                    <div class="ml-2 fn600">¥{{ formData.roadFee && formData.roadFee / 100 }}</div>
                  </div>
                  <el-dialog v-model="dialogRoadVisible">
                    <img w-full :src="formData.roadFeePic" alt="Preview Image" />
                  </el-dialog>
                </div>
                <div class="price-item">
                  <div>停车费</div>
                  <div class="fn500 flex-center">
                    <el-button type="text" @click="dialogStopVisible = true" class="change" v-if="formData.stopFeePic">凭证</el-button>
                    <div class="ml-2 fn600">¥{{ formData.stopFee && formData.stopFee / 100 }}</div>
                  </div>
                  <el-dialog v-model="dialogStopVisible">
                    <img w-full :src="formData.stopFeePic" alt="Preview Image" />
                  </el-dialog>
                </div>
                <div class="price-item">
                  <div>住宿费</div>
                  <div class="fn500 flex-center">
                    <el-button type="text" @click="dialogStayVisible = true" class="change" v-if="formData.stayFeePic">凭证</el-button>
                    <div class="ml-2 fn600">¥{{ formData.stayFee && formData.stayFee / 100 }}</div>
                  </div>
                  <el-dialog v-model="dialogStayVisible">
                    <img w-full :src="formData.stayFeePic" alt="Preview Image" />
                  </el-dialog>
                </div>
              </template>
            </div>
            <div class="total-price">
              费用合计 <span>¥{{ data.totalFee / 100 || 0 }}</span>
            </div>
            <div class="line bg-gray-50"></div>
            <div class="price">
              <div class="price-item">
                <div>实际开始时间</div>
                <div class="fn500">{{ formatTime(formData.appStartTime) }}</div>
              </div>
              <div class="price-item">
                <div>实际结束时间</div>
                <div class="fn500">{{ formatTime(formData.appEndTime) }}</div>
              </div>
              <div class="price-item">
                <div>行驶里程</div>
                <div class="fn500 flex-center">
                  <el-button type="text" @click="handleMile" class="change" v-if="formData.dispatchStatus!=4">查看</el-button>
                  <div class="ml-2">{{ formData.appDriveMile }}公里</div>
                </div>
              </div>
              <div class="price-item">
                <div>行驶时长</div>
                <div class="fn500">{{ formatFeeTime(formData.appDriveDuration) }}</div>
              </div>
              <div class="price-item" v-if="formData.dispatchStatus == 4">
                <div>用车确认单</div>
                <div class="fn500">
                  -
                </div>
              </div>
              <div class="price-item" v-else>
                <div>用车确认单</div>
                <div class="fn500 flex-center">
                  <el-button type="text" @click="handleUseCar" class="change" v-if="formData.carConfirmPic && formData.carConfirmPic.length>0">查看</el-button>
                  <div class="ml-2">{{ formData.carConfirmPic && formData.carConfirmPic.length }}张</div>
                </div>
              </div>
            </div>
          </div>
          <div class="rent-info mt-2">
            <div class="fn600 cl3">租赁信息</div>
            <div class="bg-gray-50 rent-box">
              <div class="rent-box-item">
                <div>取车时间</div>
                <div class="mt-4">{{ formData.pickUpCarTime && formatTime(formData.pickUpCarTime) }}</div>
              </div>
              <div class="top-line">
                <div>{{ getDay() || '' }}</div>
                <div class="line-con"></div>
                <div>{{ formData.driveType && formData.driveType == 1 ? '自驾' : '配司机' }}</div>
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
        </div>
      </div>
    </Dialog>
    <el-dialog v-model="mileVisible" title="行驶里程" width="600">
      <div class="mile">
        <div class="cl3 fn600 fs18">总里程：{{ formData.appDriveMile }}公里</div>
        <div class="cl9 mt-4">仪表初始里程</div>
        <div class="mt-2">{{ formData.initMileage }}公里</div>
        <div class="mt-4 cl9">仪表初始照片</div>
        <div class="mt-2">
          <el-image style="width: 339px; height: 123px" :src="formData.initImg" :zoom-rate="1.2" :max-scale="7"
            :min-scale="0.2" :preview-src-list="[formData.initImg]" :initial-index="0" fit="cover" />
        </div>
        <div class="mt-4 cl9">仪表结束里程</div>
        <div class="mt-2">{{ formData.endMileage }}公里</div>
        <div class="mt-4 cl9">仪表结束照片</div>
        <div class="mt-2"><el-image style="width: 339px; height: 123px" :src="formData.endImg" :zoom-rate="1.2"
            :max-scale="7" :min-scale="0.2" :preview-src-list="[formData.endImg]" :initial-index="0" fit="cover" /></div>
      </div>
    </el-dialog>
    <el-dialog v-model="usecarVisible" title="用车确认单" width="750">
      <div class="usecar">
        <el-image style="width: 339px; height: 123px" :src="item" :zoom-rate="1.2" :max-scale="7" v-for="(item, index) in formData.carConfirmPic" :key="index"
            :min-scale="0.2" :preview-src-list="[formData.carConfirmPic]" :initial-index="0" fit="cover" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { feeSettlementDetail } from "@/api/fee";
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
const visible = ref(false);
const mileVisible = ref(false)
const loading = ref(false)
watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
    getData();
  },
  { immediate: true }
);
watch(
  () => visible.value,
  (val) => {
    emit("update:modelValue", val);
  }
);

function handleMile() {
  mileVisible.value = true
}
const usecarVisible = ref(false)
function handleUseCar() {
  usecarVisible.value = true
}

const dialogRoadVisible = ref(false)
const dialogStopVisible = ref(false)
const dialogStayVisible = ref(false)
const dialogVisible = ref(false)
const data = ref({})
function getData() {
  console.log(props.id, 999)
  loading.value = true
  feeSettlementDetail({ keyId: props.id }).then((res) => {
    formData.value = res
    data.value = res.orgQuotedList[0]
    formData.value.carConfirmPic = res.carConfirmPic && res.carConfirmPic.split(";") || []
    console.log(formData.value,1)
  }).finally(()=>{
    loading.value = false
  });
}

function getDay() {
  let t = (formData.value.backCarTime - formData.value.pickUpCarTime) / 1000
  return formatTopTime(t)
}

function formatTopTime(t) {
  if (t) {
    let d = parseInt(t / 60 / 60 / 24)
    let h = parseInt(t / 60 / 60 % 24)
    console.log(t, d, h, 999)
    if (d == 0) {
      return `${h > 0 ? h : 1}小时`
    } else if (h == 0) {
      return `${d}天`
    }
    return `${d}天${h}小时`
  }
  return ''
}

function formatFeeTime(t) {
  if (t) {
    let h = parseInt(t / 60 / 60 % 24)
    let m = parseInt(t / 60 % 60)
    if (h == 0) {
      return `${m > 0 ? m : 1}分钟`
    } else if (m == 0) {
      return `${h}小时`
    }
    return `${h}小时${m}分钟`
  }
  return ''
}
</script>

<style lang="scss" scoped>
:deep(.el-dialog__body) {
  padding: 0;
}

.cl3 {
  color: #333333;
}

.fs12 {
  font-size: 12px;
}

.fs18 {
  font-size: 18px;
}

.flex-center {
  display: flex;
  align-items: center;
}

.cl9 {
  color: #999999;
}

.flex-btw {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  padding: 3px 15px;
}

.mile {
  padding: 20px;

  img {
    width: 339px;
    height: 123px;
  }
}
.usecar {
  display: flex;
  padding: 20px;
  justify-content: space-between;
  flex-wrap: wrap;
  img {
    margin-bottom: 15px;
  }
}

.line {
  height: 1px;
  margin-top: 20px;
  background: #666666;
  opacity: 0.2;
  margin-bottom: 20px;
}

.fn600 {
  font-weight: 600;
}

.fn500 {
  font-weight: 500;
}

.fn400 {
  font-weight: 400;
}

.con {
  padding: 10px;
}

.tips {
  color: rgb(251, 145, 17);
  background: #fff;
  text-align: center;
  height: 74px;
  line-height: 74px;
  font-size: 16px;
}

.car {
  padding: 10px 20px;
  background: #fff;
  margin-top: 10px;
}

.carinfo {
  display: flex;
  align-items: center;
  margin-top: 15px;
  position: relative;

  .disstatus {
    position: absolute;
    right: 20px;
    top: 5px;
    font-size: 18px;
    font-weight: 600;
  }


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
  line-height: 32px;
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

.rent-info {
  background: #fff;
  padding: 20px;
  position: relative;

  .status {
    position: absolute;
    right: 20px;
    top: 20px;
    color: rgb(66, 110, 243);
    background: #ecf5ff;
    font-size: 12px;
    padding: 5px 8px;
    border-radius: 3px;
  }

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

    .top-line {
      flex: 1;
      text-align: center;

      .line-con {
        height: 2px;
        background: #666666;
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
      background: red;
      margin-right: 10px;
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
</style>
