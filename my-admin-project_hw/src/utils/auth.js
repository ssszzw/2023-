const AccessTokenKey = 'ACCESS_TOKEN'
const MenuInfo='MENU_INFO'

// 获取 Token
export function getAccessToken() {
  return localStorage.getItem(AccessTokenKey)
}
// 设置 Token
export function setToken(token) {
  localStorage.setItem(AccessTokenKey, token)
}
// 删除 Token
export function removeToken() {
  localStorage.removeItem(AccessTokenKey)
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