import useUserStore from '@/store/modules/user';
import { mainRoutes, globalRoutes } from '@/router';
import Layout from '@/layout';
import ParentView from '@/layout/parentView';
import { shallowRef } from 'vue';

// 匹配views里面所有的.vue文件
const modules = import.meta.glob('./../../views/**/*.vue');

export default defineStore(
    'permission',
    {
        state: () => ({
            whiteList: [], // 免登白名单
        }),
        actions: {
            // 获取免登名单
            getWhiteList() {
                const globalPathList = getRoutesPath(globalRoutes); // 获取公共路由path
                return [...this.whiteList, ...globalPathList];
            },
            // 生成动态路由
            generateRoutes() {
                return new Promise((resolve, reject) => {
                    // 异步路由
                    const asyncRoutes = filterAsyncRouter(mainRoutes);
                    resolve(asyncRoutes);
                });
            },
        }
    }
);

// 获取公共路由的所有path
function getRoutesPath(list = [], res = [], basePath = '/') {
    list.forEach(item => {
        const path = resolvePath(item.path, basePath);
        res.push(path);
        if (item.children && item.children.length) {
            getRoutesPath(item.children, res, path);
        }
    });
    return res;
}

// 根据权限过滤路由
function filterAsyncRouter(list, res = [], parentPath) {
    const len = list.length;
    for (let i = 0; i < len; i++) {
        const route = { ...list[i] };
        // 检查权限值
        let routePermission = route.permission || [];
        if (typeof routePermission === 'number') {
            routePermission = [routePermission];
        }
        const userPermission = useUserStore().permissions;
        const hasPer = routePermission.every(ele => userPermission.includes(ele));
        if (!hasPer) {
            continue;
        }
        // 处理标题
        if (!route.meta && route.title) {
            route.meta = { title: route.title };
        }
        // 处理path
        route.path = resolvePath(route.path, parentPath);
        // 处理component
        if (route.component === 'Layout') {
            route.component = shallowRef(Layout)
        } else if (route.component === 'ParentView') {
            route.component = shallowRef(ParentView)
        } else if (typeof route.component === 'string') {
            route.component = loadView(route.component)
        }
        // 处理children
        if (route.children && route.children.length) {
            // route.children = filterAsyncRouter(route.children, res, route.path);
            const children = filterAsyncRouter(route.children, [], route.path);
            if (children && children.length) {
                route.children = children;
                route.redirect = resolvePath(route.children[0].path, route.path);
            } else {
                // children没有值，跳过此路由
                continue;
            }
        }
        if (route.component) {
            res.push(route);
        }
    }
    return res;
}

// 获取全路径
function resolvePath(routePath, parentPath) {
    const getFullPath = path => {
        if (!path) return '';
        const hasBasePath = path.split('')[0] === '/';
        return hasBasePath ? path : `/${path}`;
    };
    if (routePath.includes('/')) {
        // 全路径
        return getFullPath(routePath);
    }
    // 子路径
    return getFullPath(parentPath) + getFullPath(routePath);
}

// 引入路由component
function loadView(component) {
    let res;
    const view = resolvePath(component);
    for (const path in modules) {
        const dir = path.split('/views')[1].split('.vue')[0];
        if (dir === view || dir === `${view}/index`) {
            // 有的path是指向文件夹，加/index得到文件路径
            res = () => modules[path]();
        }
    }
    return res;
}
