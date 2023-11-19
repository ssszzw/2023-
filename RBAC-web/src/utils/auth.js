const AccessTokenKey = 'ACCESS_TOKEN'
const RefreshTokenKey = 'REFRESH_TOKEN'

const MenuInfo='MENU_INFO'

// 获取 ACCESS_TOKEN
export function getAccessToken() {
  return localStorage.getItem(AccessTokenKey)
}
// 获取 REFRESH_TOKEN
export function getRefreshToken() {
  return localStorage.getItem(RefreshTokenKey)
}
export function setToken(token) {
  localStorage.setItem(AccessTokenKey, token.accessToken)
  localStorage.setItem(RefreshTokenKey, token.refreshToken)
}

export function removeToken() {
  localStorage.removeItem(AccessTokenKey)
  localStorage.removeItem(RefreshTokenKey)
}

// //设置 MenuInfo
// export function setMenuInfo(menu) {
//   localStorage.setItem(MenuInfo,menu)
// }
// // 获取 MenuInfo
// export function getMenuInfo() {
//   return localStorage.getItem(MenuInfo)
// }
// // 删除 MenuInfo
// export function removeMenuInfo() {
//   localStorage.removeItem(MenuInfo)
// }