import request from '@/utils/request';

// 查询注册用户列表
export function queryRegUserList(query) {
    return request({
        url: '/hw-opt-server/regUser/queryRegUserList',
        method: 'get',
        params: query
    })
}

// 注册配送站点信息
export function findStationInfo(userId) {
    return request({
        url: '/hw-opt-server/regUser/findStationInfo',
        method: 'get',
        params: { userId }
    })
}

// 查询账号列表
export function queryUserList(data) {
    return request({
        url: '/zoom-fleet-manager/user/queryUserList',
        method: 'post',
        data: data
    })
}

// 更新账号状态
export function updateStatus(data) {
    return request({
        url: '/hw-opt-server/user/updateStatus',
        method: 'post',
        data: data
    })
}

// 删除账号
export function deleteUser(userId) {
    return request({
        url: '/zoom-fleet-manager/user/deleteUser',
        method: 'get',
        params: { userId }
    })
}

// 重置密码
export function resetPassword(userId) {
    return request({
        url: '/zoom-fleet-manager/user/resetPassword',
        method: 'post',
        data: { userId }
    })
}

// 员工详情
export function findUserInfo(userId) {
    return request({
        url: '/zoom-fleet-manager/user/userView',
        method: 'get',
        params: { userId }
    })
}

// 添加或修改账号信息
export function addOrUpdateUser(data) {
    return request({
        url: '/zoom-fleet-manager/user/addOrUpdateUser',
        method: 'post',
        data: data
    })
}