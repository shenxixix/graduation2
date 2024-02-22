import request from '@/utils/request';

// 查询角色列表
export function queryRoleList(query) {
    return request({
        url: '/zoom-fleet-manager/role/queryRoleList',
        method: 'get',
        params: query
    })
}

// 删除角色
export function deleteRole(roleId) {
    return request({
        url: '/zoom-fleet-manager/role/deleteRole',
        method: 'get',
        params: { roleId }
    })
}

// 角色详细信息
export function findRoleInfo(roleId) {
    return request({
        url: '/zoom-fleet-manager/role/findRoleInfo',
        method: 'get',
        params: { roleId }
    })
}

// 添加或修改角色信息
export function addOrUpdateRole(data) {
    return request({
        url: '/zoom-fleet-manager/role/addOrUpdateRole',
        method: 'post',
        data: data
    })
}

// 查询所有菜单列表
export function queryAllMenuList(roleId) {
    return request({
        url: '/zoom-fleet-manager/role/queryAllMenuList',
        method: 'get',
        params: { roleId }
    })
}