import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  if(Cookies.get(TokenKey)){
  return Cookies.get(TokenKey)

  }else{
    return sessionStorage.getItem('token')
  }
}

export function setToken(token) {
  if(Cookies.set(TokenKey, token)){
  return Cookies.set(TokenKey, token)

  }else{
    return sessionStorage.setItem('token', token)
  }
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
