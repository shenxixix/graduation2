import request from '@/utils/request'

// 查询短信通知手机号列表
export function querySmsNoticeList(query) {
    return request({
        url: '/zoom-fleet-manager/smsNotice/querySmsNoticeList',
        method: 'get',
        params: query
    })
}

export function fixDriverList(data) {
    return request({
        url: '/zoom-fleet-manager/driver/fixDriverList',
        method: 'post',
        data: data
    })
}

export function addOrUpdateFixDriver(data) {
    return request({
        url: '/zoom-fleet-manager/driver/addOrUpdateFixDriver',
        method: 'post',
        data: data
    })
}

export function importDriver(data) {
    return request({
        url: '/zoom-fleet-manager/driver/importDriver',
        method: 'post',
        data: data,
        upload: true
    })
}

export function deleteFixDriver(data) {
    return request({
        url: '/zoom-fleet-manager/driver/deleteFixDriver',
        method: 'post',
        data: data
    })
}