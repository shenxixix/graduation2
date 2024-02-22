import request from '@/utils/request'

// 报价详情
export function quotedInfoDetail(query) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/quotedInfoDetail',
        method: 'get',
        params: query
    })
}

export function upQuotedDetail(query) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/upQuotedDetail',
        method: 'get',
        params: query
    })
}

//待报价列表
export function toBeQuotedList(data) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/toBeQuotedList',
        method: 'post',
        data: data
    })
}
//已报价列表
export function quotedList(data) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/quotedList',
        method: 'post',
        data: data
    })
}
//未报价列表
export function notQuotedList(data) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/notQuotedList',
        method: 'post',
        data: data
    })
}

//出车待办列表
export function drivingTodoList(data) {
    return request({
        url: '/zoom-fleet-manager/drivingTodo/drivingTodoList',
        method: 'post',
        data: data
    })
}
//报价
export function handleQuoted(data) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/handleQuoted',
        method: 'post',
        data: data
    })
}
//取消报价
export function cancelQuoted(data) {
    return request({
        url: '/zoom-fleet-manager/quotationTodo/cancelQuoted',
        method: 'post',
        data: data
    })
}

export function dispatchTodoList(data) {
    return request({
        url: '/zoom-fleet-manager/drivingTodo/dispatchTodoList',
        method: 'post',
        data: data
    })
}

export function upDispatchDetail(query) {
    return request({
        url: '/zoom-fleet-manager/drivingTodo/upDispatchDetail',
        method: 'get',
        params: query
    })
}

export function handleDispatch(data) {
    return request({
        url: '/zoom-fleet-manager/drivingTodo/handleDispatch',
        method: 'post',
        data: data
    })
}

export function fixedEpDetail(query) {
    return request({
        url: '/zoom-fleet-manager/fixedEp/fixedEpDetail',
        method: 'get',
        params: query
    })
}

export function updateFixedEp(data) {
    return request({
        url: '/zoom-fleet-manager/fixedEp/updateFixedEp',
        method: 'post',
        data: data
    })
}

