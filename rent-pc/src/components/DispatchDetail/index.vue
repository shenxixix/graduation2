<template>
  <div>
    <Dialog v-model="visible" title="派车单详情" :padding="0" :footerShow="false">
      <div class="bg-gray-50 con" v-loading="loading">
        <div class="content">
          <div class="car">
            <div class="carinfo flex">
              <div class="logo"><img :src="data.carPic"></div>
              <div>
                <div class="name">{{ data.carModelName }}</div>
                <div class="type">{{ data.carDesc || '--' }}</div>
              </div>
              <div class="disstatus">{{ formData.dispatchStatus == 2 ? '已结束' : formData.dispatchStatus == 4 ? '已取消' : '' }}</div>
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
          <div class="car">
            <div class="fn600 cl3">报价信息</div>
            <div class="fs12 mt-4">{{ data.limitMileType == 1 ? '租期内无里程限制' : `租期内有里程限制，共计${data.limitMileSingle || 0}公里`
            }}</div>
            <div class="fs12 mt-2">超公里{{ data.beyondMileFee / 100 || 0 }}元/公里，超小时{{ data.beyondTimeFee / 100 || 0 }}元/小时
            </div>
            <div v-for="(item, index) in formData.orgQuotedList" :key="index">
              <div class="line bg-gray-50"></div>
              <div class="price" v-for="(e, index1) in item.carFeeList" :key="index1">
                <div class="mt-4 fn600">车{{ index1 + 1 }}:{{ e.carAge }}年车龄</div>
                <div class="price-item">
                  <div>租车费</div>
                  <div class="fn600">¥{{ e.singleRentFee && e.singleRentFee / 100 }}</div>
                </div>
                <div class="price-item">
                  <div>驾驶员费用</div>
                  <div class="fn600">¥{{ e.singleDriverFee && e.singleDriverFee / 100 }}</div>
                </div>
                <div class="price-item price-item-total">
                  <div class="fn600"><span class="cl9 mr-2 fn400">小计</span>¥{{ e.subtotal && e.subtotal / 100 }}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="rent-info">
            <div class="fn600 cl3">租赁信息</div>
            <div class="bg-gray-50 rent-box">
              <div class="rent-box-item">
                <div>取车时间</div>
                <div class="mt-4">{{ formData.pickUpCarTime && formatTime(formData.pickUpCarTime) }}</div>
              </div>
              <div class="top-line">
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
        </div>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import { quotedInfoDetail } from "@/api/deal";
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
  dispatchId: {
    type: String,
    default: "",
  },
  type: {
    type: String,
    default: "",
  },
});
const formData = ref({
});
const visible = ref(false);
const data = ref({})
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
watch(
  () => props.id,
  (val) => {
    if (val) {
      getData();
    }
  },
  { immediate: true }
);

const dialogVisible = ref(false)

function getPrice(i) {
  let p = 0
  formData.value.orgQuotedList[i].carFeeList.forEach(e => {
    p += (Number(e.singleDriverFee) + Number(e.singleRentFee))
  })
  return p
}

function getData() {
  console.log(props.keyId, 123)
  loading.value = true
  quotedInfoDetail({ keyId: props.id, dispatchId: props.dispatchId }).then((res) => {
    formData.value = res;
    data.value = res.orgQuotedList[0]
  }).finally(()=>{
    loading.value = false
  });
}

</script>

<style lang="scss" scoped>
.cl3 {
  color: #333333;
}

.fs12 {
  font-size: 12px;
}

.cl9 {
  color: #999999;
}

.line {
  height: 1px;
  margin-top: 20px;
  background: #666666;
  opacity: 0.2;
}

.fn600 {
  font-weight: 600;
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
  margin-bottom: 15px;
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

.rent-info {
  background: #fff;
  padding: 20px;
  margin-top: 15px;

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

.flex-btw {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  padding: 3px 15px;
}
</style>
