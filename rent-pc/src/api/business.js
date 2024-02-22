import request from '@/utils/request'

// 企业列表
export function queryEnterpriseList(query) {
    return request({
        url: '/hw-opt-server/enterprise/queryEnterpriseList',
        method: 'get',
        params: query
    })
}

// 企业分页列表
export function queryEnterprisePage(query) {
    return request({
        url: '/hw-opt-server/enterprise/queryEnterprisePage',
        method: 'get',
        params: query
    })
}

// 删除企业信息
export function deleteEnterprise(keyId) {
    return request({
        url: '/hw-opt-server/enterprise/deleteEnterprise',
        method: 'post',
        data: { keyId }
    })
}

// 添加或修改企业信息
export function addOrUpdateEnterprise(data) {
    return request({
        url: '/hw-opt-server/enterprise/addOrUpdateEnterprise',
        method: 'post',
        data: data
    })
}

// 更新企业状态
export function updateStatus(data) {
    return request({
        url: '/hw-opt-server/enterprise/updateStatus',
        method: 'post',
        data: data
    })
}

// 查询企业配送站点分页列表
export function queryEnterpriseStationPage(query) {
    return request({
        url: '/hw-opt-server/enterprise/queryEnterpriseStationPage',
        method: 'get',
        params: query
    })
}

// 删除配送站点信息
export function deleteStation(keyId) {
    return request({
        url: '/hw-opt-server/enterprise/deleteStation',
        method: 'get',
        params: { keyId }
    })
}

// 查询配送站点信息
export function findStationInfo(keyId) {
    return request({
        url: '/hw-opt-server/enterprise/findStationInfo',
        method: 'get',
        params: { keyId }
    })
}