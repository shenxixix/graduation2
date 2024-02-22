import request from '@/utils/request'

// 资金方列表
export function queryFundList(data) {
    return request({
        url: '/hw-opt-server/queryInvertorUserList',
        method: 'post',
        data: data
    })
}

// 添加/修改资金方信息
export function addOrUpdateFund(data) {
    return request({
        url: '/hw-opt-server/addOrUpdateInvertor',
        method: 'post',
        data: data
    })
}

// 资金方信息
export function findFundInfo(query) {
    return request({
        url: '/hw-opt-server/findInvertorInfo',
        method: 'get',
        params: query
    })
}

// 更新资金方状态
export function updateFundStatus(data) {
    return request({
        url: '/hw-opt-server/updateInvertorStatus',
        method: 'post',
        data: data
    })
}