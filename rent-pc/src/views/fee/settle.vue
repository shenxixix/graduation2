<template>
  <div>
    <Dialog v-model="visible" title="结算" :padding="0" @confirm="confirm" width="big" :confirmLoading="confirmLoading">
      <div class="con">
        <div class="tips">确定将以下订单的状态更改为已结算吗？操作不可逆，请谨慎操作</div>
        <div>
          <el-table :data="list" border :scrollbar-always-on="true" style="width: 100%">
          <el-table-column label="租赁费用" align="center" prop="rentFeeInfo" :show-overflow-tooltip="true" min-width="120" />
          <el-table-column label="已派车辆" align="center" prop="license" :show-overflow-tooltip="true" min-width="120" />
          <el-table-column label="驾驶员" align="center" prop="driverName" :show-overflow-tooltip="true" min-width="120" />
          <el-table-column label="送取车人" align="center" prop="obtainName" :show-overflow-tooltip="true"
            min-width="120" />
          <el-table-column label="用车人" align="center" prop="useUserName" :show-overflow-tooltip="true" min-width="120" />
          <el-table-column label="用车人手机" align="center" prop="useUserMobile" :show-overflow-tooltip="true"
            min-width="120" />
          <el-table-column label="用车单位" align="center" prop="useUserOrgName" :show-overflow-tooltip="true"
            min-width="120">
          </el-table-column>
        </el-table>
        </div>
      </div>
    </Dialog>
  </div>
</template>

<script setup>
import { batchSettlement } from "@/api/fee";
import { reactive, watch } from "vue";

const { proxy } = getCurrentInstance();
const emit = defineEmits();
const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  data: {
    type: Array,
    default: [],
  },
});
const list = ref([])
const visible = ref(false);
const confirmVisible = ref(false);

watch(
  () => props.modelValue,
  (val) => {
    visible.value = val;
    console.log(props.data,999)
    list.value = props.data
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

// 提交表单
const confirmLoading = ref(false);
function confirm() {
  confirmLoading.value = true;
    let p = list.value.map(o=>o.dispatchId)
    batchSettlement({dispatchIds: p})
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
.cl3 {
  color: #333333;
}

.fs12 {
  font-size: 12px;
}

.flex-center {
  display: flex;
  align-items: center;
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

.fn500 {
  font-weight: 500;
}

.fn400 {
  font-weight: 400;
}

.con {
  padding: 20px;
}

.tips {
  color: rgb(251, 145, 17);
  font-size: 16px;
  margin-bottom: 20px;
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
  .status {
    position: absolute;
    right: 0;
    top: 0;
    color: rgb(66, 110, 243);
    background: #ecf5ff;
    font-size: 12px;
    padding: 5px 8px;
    border-radius: 3px;
  }

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
}</style>
