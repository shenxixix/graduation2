import request from '@/utils/request';

// 更新客户认证状态
export function updateStationAuthStatus(data) {
    return request({
        url: '/hw-opt-server/auth/updateStationAuthStatus',
        method: 'post',
        data: data
    })
}

// 客户认证列表
export function queryStationAuthList(query) {
    return request({
        url: '/hw-opt-server/auth/queryStationAuthList',
        method: 'get',
        params: query
    })
}

// 查询客户认证信息
export function findStationAuthInfo(keyId) {
    return request({
        url: '/hw-opt-server/auth/findStationAuthInfo',
        method: 'get',
        params: { keyId }
    })
}

// 更新骑手认证状态
export function updateRiderAuthStatus(data) {
    return request({
        url: '/hw-opt-server/auth/updateRiderAuthStatus',
        method: 'post',
        data: data
    })
}

// 骑手认证列表
export function queryRiderAuthList(query) {
    return request({
        url: '/hw-opt-server/auth/queryRiderAuthList',
        method: 'get',
        params: query
    })
}

// 查询骑手认证信息
export function findRiderAuthInfo(keyId) {
    return request({
        url: '/hw-opt-server/auth/findRiderAuthInfo',
        method: 'get',
        params: { keyId }
    })
}
