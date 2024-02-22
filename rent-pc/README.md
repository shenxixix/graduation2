# 重庆公车在线-企业服务平台

## 简介

* 重庆公车在线-企业服务平台
* vue3 + vite + element
* [测试服地址](http://hwfund.car-siem.com)
* 测试账号：19800000000 123456qq
* 默认账号：13500000001 1234aaa!

### 启动

* `node`版本14+，推荐`v14.20.0`
* `npm install`
* `npm run dev`

### 开发

* css引入了`tailwind`框架，[官网](https://www.tailwindcss.cn/)
* 样式兼容引入了`postcss`
* `vite`插件集中归纳到`/vite`目录
* `/components`目录尽量放入全局组件
* `svg`注入，图标放在`/assets/icons/svg`，使用`<svg-icon name="genzong" />`
* 有些若依的代码还未清理，后续`todo`

### 打包

* 输出目录：`dist`
* 打包：`npm run build`

### 目录

```js
src
├── App.vue
├── api                                 // 接口api
│   ├── authen.js
│   ├── login.js
│   ├── order.js
│   ├── role.js
│   ├── user.js
│   └── warehouse.js
├── assets
│   ├── 401_images
│   ├── 404_images
│   ├── icons                           // svg图标
│   ├── images                          // 图片
│   ├── static                          // 静态资源
│   └── styles                          // css
├── components
│   ├── Dialog                          // 弹窗
│   ├── DictTag                         // tag
│   ├── Pagination                      // 分页
│   └── SvgIcon                         // svg
├── layout                              // 主体布局
│   ├── components
│   ├── index.vue
│   └── parentView.vue
├── main.js
├── menu.js                             // 菜单数据，目前是前端控制
├── permission.js                       // 权限控制
├── plugins                             // 插件目录
│   ├── cache.js
│   ├── element.js
│   └── svgicon.js
├── router                              // 路由
│   └── index.js
├── store                               // vuex
│   ├── index.js
│   └── modules
├── utils                               // 工具函数
│   ├── auth.js
│   ├── dict.js
│   ├── errorCode.js
│   ├── request.js
│   ├── ruoyi.js
│   ├── scroll-to.js
│   └── wgs.js
└── views                               // 页面目录
    ├── 403.vue
    ├── 404.vue
    ├── finance                         // 财务
    ├── home                            // 主页
    ├── login                           // 登录
    ├── operate                         // 运营
    ├── risk                            // 风控
    ├── setting                         // 设置
    └── warehouse                       // 仓库

```
