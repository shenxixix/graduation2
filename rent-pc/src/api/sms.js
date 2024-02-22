import request from '@/utils/request'

// 查询短信通知手机号列表
export function querySmsNoticeList(query) {
    return request({
        url: '/zoom-fleet-manager/smsNotice/querySmsNoticeList',
        method: 'get',
        params: query
    })
}

// 更新短信通知手机号
export function updateSmsNotice(data) {
    return request({
        url: '/zoom-fleet-manager/smsNotice/updateSmsNotice',
        method: 'post',
        data: data
    })
}