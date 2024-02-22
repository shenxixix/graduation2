# 字典组件

## 简介

* 这是一个基于ElTag的字典组件
* 常用于状态的展示

## 基础调用

* 已全局注册，直接在页面使用即可

```html
<DictTag name="userSex" value="1" />
```

## 字典转换函数

* 如果只是想获取字典`label`，建议使用全局方法`dict(key, value)`
* `key`字典索引，在`@/utils/dict.js`里面
* `value`当前值

```html
<div>{{ dict('userSex', 1) }}</div>
```

## 自定义数据

* 数据默认根据`name`获取，当主动传入`options`时，将覆盖默认
* `label`,`value`必填
* 参数可选`Props`对应的，优先级：组件参数`props` > 自定义`options` > 默认参数值

```html
<DictTag :options="userSex" value="1" />

<script setup>
    // 自定义数据
    const userSex = ref([
        { label: '男', value: 1, type: 'primary', effect: 'dark' },
        { label: '女', value: 2, type: 'danger', effect: '' },
    ]);
</script>
```

## Props

| 参数 | 说明 | 类型 | 默认值 |
|------|------|------|------|
| name | 字典索引 | *String* |  |
| value | 当前值 | *Number, String, Array* |  |
| type | 类型 | *String* | 可选值`primary / success / warning / danger / info`，默认`primary` |
| effect | 主题 | *String* | 可选值`dark / light / plain`，默认`light` |
| round | 圆角 | *Boolean* | `false` |
| size | 尺寸 | *String* | 可选值`medium / small / mini` |
| options | 数据 | *Array* |  |

## Events

| 事件名 | 说明 | 回调参数 |
|------|------|------|

## Slots

| 名称 | 说明 |
|------|------|
