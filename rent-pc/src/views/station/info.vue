<template>
    <div>
        <Dialog
            v-model="visible"
            title="商家信息"
            :footerShow="false"
            :closeOnModal="false"
            :padding="0"
        >
            <div class="p-4 bg-gray-50 flex-1">
                <div class="bg-white p-4 rounded leading-10">
                    <div class="label">| 基本信息</div>
                    <el-row>
                        <el-col :span="4" class="text-hui">企业名称</el-col>
                        <el-col :span="20">{{ formData.stationName }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">统一社会代码</el-col>
                        <el-col :span="20">{{ formData.creditCode }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">法人代表</el-col>
                        <el-col :span="20">{{ formData.legalRepName }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">法人身份证号</el-col>
                        <el-col :span="20">{{ formData.legalRepCertno }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">经办人</el-col>
                        <el-col :span="20">{{ formData.legalOptName }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">经办人身份证号</el-col>
                        <el-col :span="20">{{ formData.legalOptCertno }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">商家类别</el-col>
                        <el-col :span="20">{{
                        formData.stationType == 1
                            ? "站点"
                            : formData.stationType == 2
                            ? "租车商户"
                            : ":合作服务商"
                        }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">所属平台</el-col>
                        <el-col :span="20">{{ formData.enterpriseName }}</el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">所属城市</el-col>
                        <el-col :span="20">{{ formData.cityName }}</el-col>
                    </el-row>
                    <div class="bg-white rounded leading-10">
                        <div class="text-hui">其他资料</div>
                        <div class="grid grid-cols-3 gap-4">
                            <el-image
                                style="height: 120px"
                                class="rounded"
                                fit="cover"
                                :src="formData.carPropertyUrl"
                                :preview-teleported="true"
                                :preview-src-list="stationPics"
                            />
                            <el-image
                                style="height: 120px"
                                class="rounded"
                                fit="cover"
                                :src="formData.carOrderUrl"
                                :preview-teleported="true"
                                :preview-src-list="stationPics"
                            />
                        </div>
                    </div>
                    <div class="label mt-6">| 门店信息</div>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">门店地址</el-col>
                        <el-col :span="20" class="flex items-center justify-between">
                            <div>{{ formData.location }}</div>
                        </el-col>
                    </el-row>
                    <el-row class="border-t border-gray-50">
                        <el-col :span="4" class="text-hui">联系电话</el-col>
                        <el-col :span="20">{{ formData.phoneNum }}</el-col>
                    </el-row>
                    <div class="bg-white rounded leading-10">
                        <div class="text-hui">门脸照片</div>
                        <div class="grid grid-cols-3 gap-4">
                            <el-image
                                style="height: 120px"
                                class="rounded"
                                fit="cover"
                                :src="item"
                                :preview-teleported="true"
                                :preview-src-list="stationPics"
                                :initial-index="index"
                                v-for="(item, index) in stationPics"
                                :key="index"
                            />
                        </div>
                    </div>
                </div>
            </div>
        </Dialog>
    </div>
  </template>
  
  <script setup>
  import { findStationAuthInfo, updateStationAuthStatus } from "@/api/authen";
  
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
  
  // 弹窗
  const visible = ref(false);
  watch(
    () => props.modelValue,
    (val) => {
      visible.value = val;
      if (props.keyId) {
        getInfo();
      }
    },
    { immediate: true }
  );
  watch(
    () => visible.value,
    (val) => {
      emit("update:modelValue", val);
    }
  );
  
  // 获取详情
  const formData = ref({});
  // 图片
  const stationPics = computed(() => {
    const pics = formData.value.stationPics;
    if (pics) {
      return pics.split(",");
    }
    return [];
  });
  function getInfo() {
    findStationAuthInfo(props.keyId).then((res) => {
      formData.value = res.data;
    });
  }

  </script>
  
  <style lang="scss" scoped>
  .label {
    color: $theme;
    font-weight: 600;
  }
  </style>
  