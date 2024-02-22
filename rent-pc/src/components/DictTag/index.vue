<template>
  <div>
    <template v-for="(item, index) in list">
      <template v-if="values.includes(item.value)">
        <!-- <span
          v-if="item.elTagType == 'default' || !item.elTagType"
          :key="item.value"
          :index="index"
          class="tag"
          :class="item.elTagClass"
        >{{ item.label }}</span> -->
        <el-tag
          :disable-transitions="true"
          :key="item.value + ''"
          :index="index"
          :effect="effect || item.effect"
          :round="round || item.round"
          :size="size || item.size"
          :type="type || item.type"
        >{{ item.label }}</el-tag>
      </template>
    </template>
  </div>
</template>

<script setup>
const { proxy } = getCurrentInstance();

const props = defineProps({
  // 字典索引
  name: String,
  // 当前的值
  value: [Number, String, Array],
  // 主题
  effect: String,
  // 圆形
  round: Boolean,
  // 尺寸
  size: String,
  // 类型
  type: String,
  // 数据
  options: {
    type: Array,
    default: null,
  },
})

const values = computed(() => {
  if (props.value !== null && typeof props.value !== 'undefined') {
    return Array.isArray(props.value) ? props.value : [+props.value];
  } else {
    return [0];
  }
});

const list = ref([]);
if (props.name) {
  list.value = proxy.dict(props.name);
}
if (props.options) {
  list.value = props.options;
}
</script>

<style scoped>
.el-tag + .el-tag, .tag + .tag {
  margin-left: 10px;
}
</style>