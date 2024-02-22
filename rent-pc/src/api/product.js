import request from '@/utils/request'

export function fixProductDetail(query) {
    return request({
        url: '/zoom-fleet-manager/product/fixProductDetail',
        method: 'get',
        params: query
    })
}

export function fixProductList(data) {
    return request({
        url: '/zoom-fleet-manager/product/fixProductList',
        method: 'post',
        data: data
    })
}

export function deleteFixProduct(data) {
    return request({
        url: '/zoom-fleet-manager/product/deleteFixProduct',
        method: 'post',
        data: data
    })
}

export function updateProductStatus(data) {
    return request({
        url: '/zoom-fleet-manager/product/updateProductStatus',
        method: 'post',
        data: data
    })
}

export function addOrUpdateProduct(data) {
    return request({
        url: '/zoom-fleet-manager/product/addOrUpdateProduct',
        method: 'post',
        data: data
    })
}
