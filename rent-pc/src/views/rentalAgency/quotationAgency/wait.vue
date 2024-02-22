<template>
  <div class="p-4 bg-gray-50 min-h-full flex flex-col">
    <div class="p-4 bg-white">
      <el-form :model="queryParams" ref="queryRef" :inline="true">
        <el-form-item label="驾驶方式" prop="driveType">
          <el-select v-model="queryParams.driveType" placeholder="请选择" clearable style="width: 240px">
            <el-option label="配司机" value="2" />
            <el-option label="自驾" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="用车人" prop="useUserName">
          <el-input v-model="queryParams.useUserName" style="width: 240px" />
        </el-form-item>
        <el-form-item label="用车单位" prop="useOrgName">
          <el-input v-model="queryParams.useOrgName" style="width: 240px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="p-4 bg-white mt-4 flex-1">
      <div class="flex justify-between items-center">
        <div>待报价列表</div>
        <div>
          <SmsNotice title="报价订单" :type="1" />
          <el-button type="primary" @click="handleExport">导出</el-button>
        </div>
      </div>
      <div class="mt-4">
        <el-table v-loading="loading" :data="list" border :scrollbar-always-on="true" style="width: 100%">
          <el-table-column label="序号" align="center" prop="num" width="100" fixed="left">
            <template #default="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="报价倒计时" align="center" prop="expiredTime" :show-overflow-tooltip="true" min-width="200">
            <template #default="scope">
              <span>{{ formatCountTime(scope.row.excessTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="所需车数" align="center" prop="totalCarNum" :show-overflow-tooltip="true" min-width="200" />
          <el-table-column label="所需车型" align="center" prop="carModelInfo" :show-overflow-tooltip="true"
            min-width="200" />
          <el-table-column label="联系人" align="center" prop="userNickName" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="联系人手机" align="center" prop="userMobile" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="用车单位" align="center" prop="orgName" :show-overflow-tooltip="true" min-width="150" />
          <el-table-column label="驾驶方式" align="center" prop="driveType" :show-overflow-tooltip="true" min-width="150">
            <template #default="scope">
              <span>{{ scope.row.driveType == 1 ? '自驾' : '配司机' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="取车时间" align="center" prop="pickUpCarTime" :show-overflow-tooltip="true" min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.pickUpCarTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="还车时间" align="center" prop="backCarTime" :show-overflow-tooltip="true" min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.backCarTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="取车点" align="center" prop="startAddress" :show-overflow-tooltip="true" min-width="150">
          </el-table-column>
          <el-table-column label="还车点" align="center" prop="endAddress" :show-overflow-tooltip="true" min-width="150">
          </el-table-column>
          <el-table-column label="提交时间" align="center" prop="insertTimestamp" :show-overflow-tooltip="true"
            min-width="180">
            <template #default="scope">
              <span>{{ formatTime(scope.row.insertTimestamp) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="150" fixed="right">
            <template #default="scope">
              <el-button type="text" @click="handleQuote(scope.row.keyId)" class="change">报价</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination class="mt-4" v-show="total > 0" :total="total" v-model:page="queryParams.current"
          v-model:limit="queryParams.size" @pagination="getList" />
      </div>
    </div>
    <Add v-model="dialogVisible" v-if="dialogVisible" :keyId="keyId" @update="resetQuery"></Add>
    <Quote v-model="quoteVisible" v-if="quoteVisible" :id="keyId" @update="resetQuery"></Quote>
  </div>
</template>

<script setup>
import { toBeQuotedList } from "@/api/deal";
import Add from "./add.vue";
const { proxy } = getCurrentInstance();

// 获取列表
const queryParams = reactive({
  current: 1,
  size: 10,
  driveType: undefined,
  useUserName: undefined,
  useOrgName: undefined,
});
const loading = ref(false);
const list = ref([]);
const total = ref(0);
const dialogVisible = ref(false)
getList();
function getList() {
  // console.log(Date.parse('2023-12-15 20:00') - new Date().getTime())
  // list.value = [{ time: Date.parse('2023-12-15 20:00') }, { time: Date.parse('2023-12-15 20:10') }]
  // list.value.forEach(el => {
  //   el.excessTime = el.time - new Date().getTime();
  //   countDown(el);
  // })
  loading.value = true;
  toBeQuotedList(queryParams)
    .then((res) => {
      list.value = res.records;
      total.value = +res.total;
      console.log(new Date().getTime())
      list.value.forEach(el => {
        el.excessTime = el.quoteExpiredTime - new Date().getTime();
        countDown(el);
      })
    })
    .finally(() => {
      loading.value = false;
    });
}

function formatCountTime(time) {
  if (time > 0) {
    console.log(time,999)
    var days = Math.floor(time / 1000 / 60 / 60 / 24); //获取天数
    var hours = Math.floor(time / 1000 / 60 / 60 % 24); // 获取小时
    var minutes = Math.floor(time / 1000 / 60); //获取分钟数
    var seconds = Math.floor(time / 1000 % 60); //获取秒数
    // 判断天、时、分、秒是不是两位数，如果是直接输出，如果不是在前边加个0;
    days < 10 ? days = "0" + days : days;
    hours < 10 ? hours = "0" + hours : hours;
    minutes < 10 ? minutes = "0" + minutes : minutes;
    seconds < 10 ? seconds = "0" + seconds : seconds;
    console.log(minutes,999999999)
    var rels = `${minutes}分${seconds}秒`
  }
  // 判断时间差是不是正数，就是截止时间是不是比现在的时间晚
  var mis = time > 0 ? rels : "已超时"
  return mis
}

function countDown(row) {
  row.countDown = setInterval(() => {
    if (row.excessTime > 0) {
      row.excessTime = row.excessTime - 1000;
    }
    else {
      clearInterval(row.countDown)
    }
  }, 1000);
}
// 清除所有定时器
function clearAll(list) {
  list.value.forEach(el => {
    clearInterval(el.countDown)
  })
}

// onUnmounted(() => {
//   clearAll()
// })

// 查看
const quoteVisible = ref(false);
const keyId = ref("");
function handleQuote(id) {
  keyId.value = id;
  quoteVisible.value = true;
}

// 重置
function resetQuery() {
  queryParams.size = 10;
  queryParams.current = 1; // 页码还原
  proxy.$refs.queryRef.resetFields(); // 表单还原
  getList(); // 更新列表
}

// 搜索
function handleQuery() {
  queryParams.current = 1; // 查询第一页
  getList(); // 更新列表
}
// 导出
function handleExport() {
  proxy.download(
    "/zoom-fleet-manager/quotationTodo/exportToBeQuotedList",
    {
      ...queryParams,
      exportType: 1,
    },
    "待报价列表.xls"
  );
}
</script>

<style lang="scss" scoped>
:deep(.el-table) tr th.el-table__cell {
  background: $theme;
  color: white;
}

.change {
  color: rgb(0, 128, 255);
}
</style>
