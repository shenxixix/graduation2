# 弹窗组件

## 简介

* 这是一个基于element的dialog弹窗组件
* 自定义了header和footer

## 基础调用

* `components`组件已全局注册，直接在页面使用即可

```html
<Dialog v-model="visible" title="这是标题">
    <div v-for="i in 20" :key="i" style="line-height: 40px;">一些内容{{ i }}</div>
</Dialog>
```

## 自定义底部栏 `footer`

* 使用了`footer`插槽，会覆盖原有的底部栏，如果要完全不显示底部栏，设置`footerShow`为`false`

```html
<Dialog v-model="visible" title="自定义底部栏">
    <div v-for="i in 20" :key="i" style="line-height: 40px;">一些内容{{ i }}</div>
    <template #footer>
        <el-button>拒绝</el-button>
        <el-button type="primary">通过</el-button>
    </template>
</Dialog>
```

## 弹窗大小 `width`

* 支持字符串`small`, `middle`, `big`，也支持固定px和百分比，默认值`middle`

```html
<Dialog v-model="visible" title="弹窗大小" width="big">
    <div>一个大弹窗</div>
</Dialog>
```

## 弹窗高度 `height`

* 支持字符串`max`, `auto`，也支持固定px和百分比，默认值`auto`

```html
<Dialog v-model="visible" title="弹窗高度" height="max">
    <div>弹窗最大高度是90vh，主体内容部分flex-1，超过显示滚动条</div>
</Dialog>
```

## 内边距 `padding`

* 内容部分默认一个内边距`padding`，值为`30px`，如要取消，手动设置为`0`即可

```html
<Dialog v-model="visible" title="内边距" padding="0">
    <div>没有内边距</div>
</Dialog>
```

## Props

| 参数 | 说明 | 类型 | 默认值 |
|------|------|------|------|
| v-model/visible | 弹窗开关 | *Boolean* | `false` |
| padding | 弹窗主体内边距 | *Number* | `30` |
| footerShow | 是否显示底部栏 | *Boolean* | `true` |
| confirmShow | 是否显示确认按钮 | *Boolean* | `true` |
| confirmLoading | 确认按钮loading | *Boolean* | `false` |
| confirmText | 确认按钮文字 | *String* | `确定` |
| cancelShow | 是否显示取消按钮 | *Boolean* | `true` |
| cancelText | 取消按钮文字 | *String* | `取消` |
| title | 标题，为空时不显示标题栏 | *String* |  |
| width | 弹窗大小: small, middle, big，也支持固定px和百分比 | *String* | `middle` |
| height | 弹窗高度: max, auto，也支持固定px和百分比 | *String* | `auto` |
| mask | 是否需要遮罩层 | *Boolean* | `true` |
| appendToBody | Dialog 自身是否插入至 body 元素上，嵌套dialog需要设置为true | *Boolean* | `false` |
| closeOnModal | 是否可以通过点击 modal 关闭 Dialog | *Boolean* | `true` |
| closeOnEsc | 是否可以通过按下 ESC 关闭 Dialog | *Boolean* | `true` |
| destroyOnClose | 关闭时是否销毁Dialog中的元素 | *Boolean* | `true` |

## Events

| 事件名 | 说明 | 回调参数 |
|------|------|------|
| change | 弹窗开关时触发 | event |
| confirm | 点击确定时触发 | event |
| close | 点击取消时触发 | event |

## Slots

| 名称 | 说明 |
|------|------|
| default | 默认插槽，弹窗主体内容 |
| footer | 底部插槽，替换预设底部栏 |
