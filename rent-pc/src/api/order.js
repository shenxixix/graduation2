import request from '@/utils/request'

// 退租订单列表
export function queryRefundOrderPage(query) {
    return request({
        url: '/hw-car-business/rent/queryRefundOrderPage',
        method: 'get',
        params: query
    })
}

// 租赁订单列表
export function queryRentOrderPage(query) {
    return request({
        url: '/hw-car-business/rent/queryRentOrderPage',
        method: 'get',
        params: query
    })
}

// 添加或修改商品信息
export function addOrUpdateGoods(data) {
    return request({
        url: '/hw-car-business/rent/addOrUpdateStationGoods',
        method: 'post',
        data: data
    })
}

// 查询商品信息
export function findGoodsInfo(keyId) {
    return request({
        url: '/hw-car-business/rent/findGoodsInfo',
        method: 'get',
        params: { keyId }
    })
}

// 查询商品列表
export function queryGoodsPage(query) {
    return request({
        url: '/hw-car-business/rent/queryGoodsPage',
        method: 'get',
        params: query
    })
}

// 删除商品信息
export function deleteGoods(keyId) {
    return request({
        url: '/hw-car-business/rent/deleteGoods',
        method: 'post',
        data: { keyId }
    })
}

// 查询合同分页列表
export function queryContractPage(query) {
    return request({
        url: '/hw-car-business/rent/queryContractPage',
        method: 'get',
        params: query
    })
}

// 查询合同列表
export function queryContractList(query) {
    return request({
        url: '/hw-car-business/rent/queryContractList',
        method: 'get',
        params: query
    })
}

// 添加或修改合同信息
export function addOrUpdateContract(data) {
    return request({
        url: '/hw-car-business/rent/addOrUpdateContract',
        method: 'post',
        data: data
    })
}

// 删除合同信息
export function deleteContract(keyId) {
    return request({
        url: '/hw-car-business/rent/deleteContract',
        method: 'post',
        data: { keyId }
    })
}

// 查看合同详情
export function queryContractDetail(keyId) {
    return request({
        url: '/hw-car-business/rent/queryContractDetail',
        method: 'post',
        data: { keyId }
    })
}

// 订单详情
export function orderDetail(orderId) {
    return request({
        url: '/hw-car-user/order/orderDetail',
        method: 'get',
        params: { orderId }
    })
}

// 租赁协议
export function orderContractUrl(orderId) {
    return request({
        url: '/hw-car-contract/orderContract/orderContractUrl',
        method: 'post',
        data: { orderId }
    })
}

// 订单下的车列表
export function stationOrderCarList(orderId) {
    return request({
        url: '/hw-car-user/order/stationOrderCarList',
        method: 'post',
        data: { orderId }
    })
}

// 租金支付记录
export function payList(orderId) {
    return request({
        url: '/hw-car-user/order/payList',
        method: 'get',
        params: { orderId }
    })
}

// 租金待支付
export function noPayList(orderId) {
    return request({
        url: '/hw-car-user/order/noPayList',
        method: 'get',
        params: { orderId }
    })
}

// 还款计划
export function payListOrder(orderId) {
    return request({
        url: '/hw-car-user/order/payList',
        method: 'get',
        params: { orderId }
    })
}

// 退还详情
export function billDetail(keyId) {
    return request({
        url: '/hw-car-user/deposit/depositDetail',
        method: 'post',
        data: { keyId }
    })
}

// 还款记录
export function recordListOrder(orderId) {
    return request({
        url: '/hw-car-user/order/childOrderList',
        method: 'get',
        params: { orderId }
    })
}

// 租车合同
export function findOrderPdf(orderId) {
    return request({
        url: '/hw-car-user/order/findOrderPdf',
        method: 'get',
        params: { orderId }
    })
}

// 取车记录
export function obtainCarRecord(orderId) {
    return request({
        url: '/hw-car-user/order/obtainCarRecord',
        method: 'get',
        params: { orderId }
    })
}

// 退车记录
export function quitCarRecord(orderId) {
    return request({
        url: '/hw-car-user/order/quitCarRecord',
        method: 'get',
        params: { orderId }
    })
}