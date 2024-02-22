<template>
    <div>
      <Dialog v-model="visible" title="报价二次确认" :padding="0">
        <div class="bg-gray-50 con">
          <div class="tips">请再次确认报价信息，提交后不可修改</div>
          <div class="car" v-for="(item,index) in formData.orgQuotedList" :key="index">
            <div class="carinfo flex">
              <div class="logo"></div>
              <div>
                <div class="name">{{ item.carModelName }}（{{ item.carNum }}辆）</div>
                    <div class="type">{{ item.carDesc || '--' }}</div>
              </div>
            </div>
            <div class="fs12 mt-4">{{ item.limitMileType ==1?'租期内无里程限制':'租期内有里程限制'}}</div>
            <div class="fs12 mt-2">超公里{{item.beyondMileFee}}元/公里，超小时{{item.beyondTimeFee}}元/小时</div>
            <div class="line bg-gray-50"></div>
            <div class="price">
              <div class="mt-4 fn600">渝A123455</div>
              <div class="price-item">
                <div>租车费</div>
                <div class="fn600">¥100</div>
              </div>
              <div class="price-item">
                <div>驾驶员费用</div>
                <div class="fn600">¥60</div>
              </div>
              <div class="price-item price-item-total">
                <div class="fn600"><span class="cl9 mr-2 fn400">小计</span>¥160</div>
              </div>
              <div class="line bg-gray-50"></div>
            </div>
            <div class="price">
              <div class="mt-4 fn600">渝A123455</div>
              <div class="price-item">
                <div>租车费</div>
                <div class="fn600">¥100</div>
              </div>
              <div class="price-item">
                <div>驾驶员费用</div>
                <div class="fn600">¥60</div>
              </div>
              <div class="price-item price-item-total">
                <div class="fn600"><span class="cl9 mr-2 fn400">小计</span>¥160</div>
              </div>
            </div>
            <div class="line bg-gray-50"></div>
            <div class="total-price">
                费用合计  <span>¥320</span>
            </div>
          </div>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="handleCancel">取消报价</el-button>
            <el-button type="primary" @click="confirm" :loading="confirmLoading">
              报价
            </el-button>
          </span>
        </template>
      </Dialog>
    </div>
  </template>
  
  <script setup>
  import { addOrUpdateFactory, findFactoryInfo } from "@/api/warehouse";
  import { reactive, watch } from "vue";
  
  const { proxy } = getCurrentInstance();
  const emit = defineEmits();
  const props = defineProps({
    modelValue: {
      type: Boolean,
      default: false,
    },
    data: {
      type: Object,
      default: {},
    },
  });
  const formData = ref({
    orgQuotedList: []
  });
  const visible = ref(false);
  
  watch(
    () => props.modelValue,
    (val) => {
      visible.value = val;
      getData()
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
    console.log(props.formData,999)
    formData.value = props.formData
  }
  // 提交表单
  const confirmLoading = ref(false);
  function confirm() {
    proxy.$refs.queryRef.validate((valid) => {
      if (!valid) {
        return;
      }
      confirmLoading.value = true;
      addOrUpdateFactory(formData.value)
        .then(() => {
          proxy.$message.success("操作成功");
          visible.value = false;
          emit("update");
        })
        .finally(() => {
          confirmLoading.value = false;
        });
    });
  }
  </script>
  
  <style lang="scss" scoped>
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
    padding: 10px 20px;
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
  .price {
  }
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
  </style>
  