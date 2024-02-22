import { createApp } from 'vue';
import App from './App.vue';
import 'virtual:svg-icons-register';
import elementPlus from "@/plugins/element";
import elementIcon from "@/plugins/svgicon";
import router from "@/router";
import store from './store';
import "normalize.css/normalize.css"; // 基础样式 CSS RESET
import "@/assets/styles/common.scss"; // 公共样式
import "@/assets/styles/element.scss"; // element样式
import "@/assets/styles/index.css"; // tailwindcss
import { formatTime, formatStrip, formatMoney, formatMile } from '@/utils/format';
import { download } from '@/utils/request';
import { sha256 } from 'js-sha256'; // 加密
import { dict } from '@/utils/dict'; // 字典
import VueFullscreen from 'vue-fullscreen'; // 全屏

const app = createApp(App);

// 全局方法挂载
app.config.globalProperties.dict = dict; // 字典
app.config.globalProperties.download = download; // 下载
app.config.globalProperties.formatTime = formatTime; // 日期转换
app.config.globalProperties.formatStrip = formatStrip; // 浮点数运算
app.config.globalProperties.formatMoney = formatMoney; // 金额转换
app.config.globalProperties.formatMile = formatMile; // 公里
app.config.globalProperties.sha256 = sha256; // 前端加密
app.config.globalProperties.amapKey = '5fbfab5b6f6d13a9bff742af384c7615'; // 高德key

app.use(router);
app.use(store);
app.use(elementPlus);
app.use(elementIcon);
app.use(VueFullscreen);

app.mount("#app");
