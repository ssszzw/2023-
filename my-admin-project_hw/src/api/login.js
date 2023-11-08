import request from '@/utils/request'

// 用户登录
export function login(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data: data
  })
}


// 获取用户信息
export function getInfo() {
  return request({
    url: '/user/getInfo',
    method: 'get'
  })
}
// 根据token获取用户信息
export function profileGet() {
  return request({
    url: '/user/profile/get',
    method: 'get'
  })
}
//添加用户
export function postUser(user) {
  return request({
    url: '/user/postUser',
    method: 'post',
    data:user
  })
}
//删除用户
export function deleteUser(username) {
  const data = {
    username
  }
  return request({
    url: '/user/deleteUser',
    method: 'delete',
    data:data
  })
}
//修改用户
export function modifyUser(user) {
  return request({
    url: '/user/putUser',
    method: 'put',
    data:user
  })
}
//搜索用户
export function searchUser(user) {
  const data = {
    id:user.id,
    name:user.name,
    gender:user.gender,
    username:user.username,
    password:user.password,
    phone:user.phone,
  }
  return request({
    url: '/user/searchUser',
    method: 'get',
    params:user
  })
}
