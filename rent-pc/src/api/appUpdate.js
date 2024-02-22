import request from '@/utils/request'

// app版本信息
export function queryAppVersion(data) {
    return request({
      url: '/zoom-fleet-manager/appVersion/queryAppVersion',
      method: 'post',
      data: data
    })
  }
  
  // app版本信息删除
export function deleteAppVersion(data) {
    return request({
      url: '/zoom-fleet-manager/appVersion/deleteAppVersion',
      method: 'post',
      data: data
    })
  }

//  app版本升级列表
export function appVersionList(data) {
    return request({
      url: '/zoom-fleet-manager/appVersion/appVersionList',
      method: 'post',
      data: data
    })
  }
  //  新增或修改app版本信息
export function addOrUpdAppVersion(data) {
    return request({
      url: '/zoom-fleet-manager/appVersion/addOrUpdAppVersion',
      method: 'post',
      data: data
    })
  }