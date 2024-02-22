import request from '@/utils/request'

export function feeSettlementDetail(query) {
    return request({
        url: '/zoom-fleet-manager/settlement/feeSettlementDetail',
        method: 'get',
        params: query
    })
}

export function needFeeSettlementList(data) {
    return request({
        url: '/zoom-fleet-manager/settlement/needFeeSettlementList',
        method: 'post',
        data: data
    })
}

export function feeSettlementList(data) {
    return request({
        url: '/zoom-fleet-manager/settlement/feeSettlementList',
        method: 'post',
        data: data
    })
}

export function batchSettlement(data) {
    return request({
        url: '/zoom-fleet-manager/settlement/batchSettlement',
        method: 'post',
        data: data
    })
}