import request from '@/utils/request';

// 押金流水列表
export function queryDepositFlowPage(query) {
    return request({
        url: '/hw-opt-server/deposit/queryDepositFlowPage',
        method: 'get',
        params: query
    })
}

// 押金总览信息
export function findDepositOverview(query) {
    return request({
        url: '/hw-opt-server/deposit/findDepositOverview',
        method: 'get',
        params: query
    })
}

// 钱包总览信息
export function findWalletOverview(query) {
    return request({
        url: '/hw-opt-server/wallet/findWalletOverview',
        method: 'get',
        params: query
    })
}

// 用户钱包分页列表
export function queryUserWalletPage(query) {
    return request({
        url: '/hw-opt-server/wallet/queryUserWalletPage',
        method: 'get',
        params: query
    })
}

// 钱包流水分页列表
export function queryWalletFlowPage(query) {
    return request({
        url: '/hw-opt-server/wallet/queryWalletFlowPage',
        method: 'get',
        params: query
    })
}

// 钱包规则分页列表
export function queryWalletRulePage(query) {
    return request({
        url: '/hw-opt-server/wallet/queryWalletRulePage',
        method: 'get',
        params: query
    })
}

// 添加或修改钱包规则
export function addOrUpdateWalletRule(data) {
    return request({
        url: '/hw-opt-server/wallet/addOrUpdateWalletRule',
        method: 'post',
        data: data
    })
}

// 钱包规则信息
export function findWalletRule(keyId) {
    return request({
        url: '/hw-opt-server/wallet/findWalletRule',
        method: 'get',
        params: { keyId }
    })
}

// 删除钱包规则
export function deleteWalletRule(keyId) {
    return request({
        url: '/hw-opt-server/wallet/deleteWalletRule',
        method: 'post',
        data: { keyId }
    })
}

// 提现规则分页列表
export function queryWithdrawRulePage(query) {
    return request({
        url: '/hw-opt-server/withdraw/queryWithdrawRulePage',
        method: 'get',
        params: query
    })
}

// 添加或修改提现规则
export function addOrUpdateWithdrawRule(data) {
    return request({
        url: '/hw-opt-server/withdraw/addOrUpdateWithdrawRule',
        method: 'post',
        data: data
    })
}

// 提现规则信息
export function findWithdrawRule(keyId) {
    return request({
        url: '/hw-opt-server/withdraw/findWithdrawRule',
        method: 'get',
        params: { keyId }
    })
}

// 删除提现规则
export function deleteWithdrawRule(keyId) {
    return request({
        url: '/hw-opt-server/withdraw/deleteWithdrawRule',
        method: 'post',
        data: { keyId }
    })
}

// 待处理提现分页列表
export function queryPendingWithdrawPage(query) {
    return request({
        url: '/hw-opt-server/withdraw/queryPendingWithdrawPage',
        method: 'get',
        params: query
    })
}

// 查询提现信息
export function findWithdrawInfo(keyId) {
    return request({
        url: '/hw-opt-server/withdraw/findWithdrawInfo',
        method: 'get',
        params: { keyId }
    })
}

// 处理提现
export function handleWithdraw(data) {
    return request({
        url: '/hw-opt-server/withdraw/handleWithdraw',
        method: 'post',
        data: data
    })
}

// 已处理提现分页列表
export function queryWithdrawPage(query) {
    return request({
        url: '/hw-opt-server/withdraw/queryWithdrawPage',
        method: 'get',
        params: query
    })
}
