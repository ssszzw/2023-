import request from '@/utils/request'

// 用户登录
export function getMenu() {

    return request({
      url: '/user/getMenu',
      method: 'get',
    })
  }

  