# 短信提醒组件

## 简介

* 公共业务组件————短信提醒

## 基础调用

* 已全局注册，直接在页面使用即可

```html
<SmsNotice title="商户认证" :type="2" />
```

## Props

| 参数 | 说明 | 类型 | 默认值 |
|------|------|------|------|
| type | 业务类型 | *Number* | 1:订车工单,2:商户审核 |
| title | 文案 | *String* | 当有新的{{ title }}时，下列手机号将收到短信推送 |

## Events

| 事件名 | 说明 | 回调参数 |
|------|------|------|
| change | 修改短信提醒 | event |

## Slots

| 名称 | 说明 |
|------|------|
