import request from '@/utils/request'

// 登录方法
export function login(data) {
  return request({
    url: '/zoom-fleet-manager/login/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 设置密码
export function updatePassword(data) {
  return request({
    url: '/zoom-fleet-manager/user/updatePassword',
    method: 'post',
    data: data
  })
}
// 忘记密码-修改密码并登陆
export function updPswAndLogin(data) {
  return request({
    url: '/zoom-fleet-manager/user/updPswAndLogin',
    method: 'post',
    data: data
  })
}
// 获取验证码
// export function authcode(data) {
//   return request({
//       url: '/zoom-fleet-manager/user/authcode',
//       method: 'post',
//       data: data
//   })
// }
export function authcode(data) {
  return request({
      url: '/zoom-fleet-manager/manage/authcode',
      method: 'post',
      data: data
  })
}


// 用户信息
export function userView() {
  return request({
      url: '/zoom-fleet-manager/user/userView',
      method: 'get',
  })
}

// 获取用户菜单
export function queryUserMenuList() {
  return request({
      url: '/zoom-fleet-manager/role/queryUserMenuList',
      method: 'get',
  })
}

// 统计数据
export function countData(data) {
  return request({
    url: '/hw-car-business/index/countData',
    method: 'post',
    data: data
  })
}
