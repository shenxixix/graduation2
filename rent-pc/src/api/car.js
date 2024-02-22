import request from '@/utils/request'

// 查询短信通知手机号列表
export function querySmsNoticeList(query) {
    return request({
        url: '/zoom-fleet-manager/smsNotice/querySmsNoticeList',
        method: 'get',
        params: query
    })
}

export function carTypeList(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/carTypeList',
        method: 'post',
        data: data
    })
}

export function delCarType(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/delCarType',
        method: 'post',
        data: data
    })
}

export function addOrUptCarType(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/addOrUptCarType',
        method: 'post',
        data: data
    })
}

export function carModelList(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/carModelList',
        method: 'post',
        data: data
    })
}

export function carBrandList(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/carBrandList',
        method: 'post',
        data: data
    })
}

export function addOrUptBrand(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/addOrUptBrand',
        method: 'post',
        data: data
    })
}

export function addOrUptCarModel(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/addOrUptCarModel',
        method: 'post',
        data: data
    })
}

export function delCarModel(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/delCarModel',
        method: 'post',
        data: data
    })
}

export function delBrand(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/delBrand',
        method: 'post',
        data: data
    })
}

export function viewCarModel(data) {
    return request({
        url: '/zoom-fleet-manager/modelStore/viewCarModel',
        method: 'post',
        data: data
    })
}

export function fixCarList(data) {
    return request({
        url: '/zoom-fleet-manager/car/fixCarList',
        method: 'post',
        data: data
    })
}

export function deleteFixCar(data) {
    return request({
        url: '/zoom-fleet-manager/car/deleteFixCar',
        method: 'post',
        data: data
    })
}

export function addOrUpdateFixCar(data) {
    return request({
        url: '/zoom-fleet-manager/car/addOrUpdateFixCar',
        method: 'post',
        data: data
    })
}

export function importCar(data) {
    return request({
        url: '/zoom-fleet-manager/car/importCar',
        method: 'post',
        data: data,
        upload: true
    })
}

export function fixedEpList(data) {
    return request({
        url: '/zoom-fleet-extend-server/fixedEp/fixedEpList',
        method: 'post',
        data: data
    })
}