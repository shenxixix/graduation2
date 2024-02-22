import { createWebHistory, createRouter } from 'vue-router';
import Layout from '@/layout';
import ParentView from '@/layout/parentView';
import { getToken } from '@/utils/auth';
import useUserStore from '@/store/modules/user';
import usePermissionStore from '@/store/modules/permission';

/**
 * 路由数据
 * 
 * permission 权限值，不传表示所有权限
 * component 组件路径，嵌套路由用'ParentView'包裹
 * activeMenu 菜单激活项，指向path
 * title 页面标题
 */

// 公共路由，不需要登录和鉴权
export const globalRoutes = [
  {
    path: '/login',
    name: 'Login',
    meta: { title: '登录' },
    component: () => import('@/views/login'),
  },
];

// 主路由，基于用户权限动态去加载
export const mainRoutes = [
  {
    path: '/',
    component: Layout,
   
    children: [
      {
        path: '/index',
        name: 'home',
        permission: [1],
        meta: { title: '首页' },
        component: () => import('@/views/home/index'),
      },
      {
        path: '/deal',
        name: 'deal',
        permission: [2],
        meta: { title: '出租待办' },
        component: ParentView,
        children: [
          {
            path: '/carDeal',
            name: 'carDeal',
            permission: [21],
            meta: { title: '派车待办' },
            component: () => import('@/views/rentalAgency/outCar'),
          },
          {
            path: '/priceDeal',
            name: 'priceDeal',
            permission: [22],
            meta: { title: '报价待办' },
            component: () => import('@/views/rentalAgency/quotationAgency'),
          },
        ],
      },
      {
        path: '/use',
        name: 'use',
        permission: [3],
        meta: { title: '费用结算' },
        component: ParentView,
        children: [
          {
            path: '/expense',
            name: 'expense',
            permission: [31],
            meta: { title: '费用结算单' },
            component: () => import('@/views/fee'),
          },
        ],
      },
      {
        path: '/asset',
        name: 'asset',
        permission: [4],
        meta: { title: '车辆管理' },
        component: ParentView,
        children: [
          {
            path: '/asset/manage',
            name: 'manageAsset',
            permission: [41],
            meta: { title: '车辆档案' },
            component: () => import('@/views/carInfo'),
          },
        ],
      },
      {
        path: '/driver',
        name: 'driver',
        permission: [5],
        meta: { title: '司机管理' },
        component: ParentView,
        children: [
          {
            path: '/driver/manager',
            name: 'drivermanager',
            permission: [51],
            meta: { title: '司机管理' },
            component: () => import('@/views/driver'),
          },
        ],
      },
      // 系统设置
      {
        path: '/setting',
        name: 'setting',
        permission: [6],
        meta: { title: '系统设置' },
        component: ParentView,
        children: [
          {
            path: '/setting/account',
            name: 'settingAccount',
            permission: [61],
            meta: { title: '账户管理' },
            component: () => import('@/views/setting/account'),
          },
          {
            path: '/setting/auth',
            name: 'settingAuth',
            permission: [62],
            meta: { title: '权限管理' },
            component: () => import('@/views/setting/auth'),
          },
          {
            path: '/unitsManager',
            name: 'unitsManager',
            permission: [64],
            meta: { title: '单位管理' },
            component: () => import('@/views/setting/unitsManager'),
          },
        ],
      },
      {
        path: '/platform',
        name: 'platform',
        permission: [7],
        meta: { title: '平台支撑' },
        component: ParentView,
        children: [
          {
            path: '/carLibrary/manage',
            name: 'carLibrary',
            permission: [71],
            meta: { title: '车型库管理' },
            component: () => import('@/views/carLibrary'),
          },
          {
            path: '/support/app',
            name: 'appSupport',
            permission: [72],
            meta: { title: '应用升级' },
            component: () => import('@/views/appUpdate'),
          },
          {
            path: '/carLibrary/carlist',
            name: 'carlist',
            permission: [73],
            meta: { title: '平台车辆' },
            component: () => import('@/views/carLibrary/carList'),
          },
        ],
      },
      {
        path: '/product',
        name: 'product',
        permission: [8],
        meta: { title: '产品管理' },
        component: ParentView,
        children: [
          {
            path: '/productManage',
            name: 'productManage',
            permission: [81],
            meta: { title: '产品管理' },
            component: () => import('@/views/productManage'),
          },
        ],
      },
      {
        path: '/403',
        name: '403',
        meta: { title: '无权限' },
        component: () => import('@/views/403'),
      },
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: "404",
    meta: { title: '网页丢失了' },
    component: () => import("@/views/404.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: globalRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
});

// 路由守卫
router.beforeEach((to, from, next) => {
  // 设置标题
  if (to.title) {
    document.title = to.title;
  }
  // 权限控制
  const permissionStore = usePermissionStore();
  if (getToken()) {
    const userStore = useUserStore();
    if (!userStore.userInfo.userId) {
      // 判断当前用户是否已拉取完user_info信息
      userStore.getUserInfo().then(() => {
        permissionStore.generateRoutes().then(accessRoutes => {
          // 动态添加可访问路由表
          accessRoutes.forEach(route => {
            router.addRoute(route)
          });
          console.log(accessRoutes,123)
          next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
        })
      })
    } else {
      next();
    }
  } else {
    // 没有token
    const whiteList = permissionStore.getWhiteList();
    if (whiteList.includes(to.path)) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页

    }
  }
});

export default router;
