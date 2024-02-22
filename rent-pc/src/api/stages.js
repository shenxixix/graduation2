import request from '@/utils/request';

// 分期合约————更新状态
export function updateTermApplyStatus(data) {
    return request({
        url: '/hw-opt-server/termAudit/updateTermApplyStatus',
        method: 'post',
        data: data
    })
}

// 分期合约————待审核列表
export function queryPendingTermAuditPage(query) {
    return request({
        url: '/hw-opt-server/termAudit/queryPendingTermAuditPage',
        method: 'get',
        params: query
    })
}

// 分期合约————已审核列表
export function queryTermAuditPage(query) {
    return request({
        url: '/hw-opt-server/termAudit/queryTermAuditPage',
        method: 'get',
        params: query
    })
}

// 分期合约————详情信息
export function findTermInfo(keyId) {
    return request({
        url: '/hw-opt-server/termAudit/findTermInfo',
        method: 'get',
        params: { keyId }
    })
}

// 分期订单————详情
export function orderDetail(orderId) {
    return request({
        url: '/hw-car-user/stationOrder/orderDetail',
        method: 'get',
        params: { orderId }
    })
}

// 分期订单————列表
export function queryTermOrderPage(query) {
    return request({
        url: '/hw-opt-server/rent/queryTermOrderPage',
        method: 'get',
        params: query
    })
}

// 分期补缴————更新审核状态
export function updateTermRepayStatus(data) {
    return request({
        url: '/hw-opt-server/termRepay/updateTermRepayStatus',
        method: 'post',
        data: data
    })
}

// 分期补缴————(待审核)分页列表
export function queryPendingTermRepayPage(query) {
    return request({
        url: '/hw-opt-server/termRepay/queryPendingTermRepayPage',
        method: 'get',
        params: query
    })
}

// 分期补缴————(已审核)分页列表
export function queryTermRepayPage(query) {
    return request({
        url: '/hw-opt-server/termRepay/queryTermRepayPage',
        method: 'get',
        params: query
    })
}

// 分期补缴————明细分页列表
export function queryTermRepayDetailPage(query) {
    return request({
        url: '/hw-opt-server/termRepay/queryTermRepayDetailPage',
        method: 'get',
        params: query
    })
}

// 分期补缴————详情信息
export function findTermRepayInfo(keyId) {
    return request({
        url: '/hw-opt-server/termRepay/findTermRepayInfo',
        method: 'get',
        params: { keyId }
    })
}