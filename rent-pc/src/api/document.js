import request from '@/utils/request';

// 支撑文档列表
export function documentList(data) {
    return request({
        url: '/hw-opt-server/opFile/fileList',
        method: 'post',
        data: data
    })
}

// 文档详情
export function viewFile(fileId) {
    return request({
        url: '/hw-opt-server/opFile/viewFile',
        method: 'get',
        params: { fileId }
    })
}

// 修改文档
export function updateFile(data) {
    return request({
        url: '/hw-opt-server/opFile/updateFile',
        method: 'post',
        data: data
    })
}