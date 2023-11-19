import router from './router'
import { getAccessToken } from '@/utils/auth'
// import {setMenuInfo,getMenuInfo,removeMenuInfo} from '@/utils/auth'
import { getMenu } from './api/menu'
import {initAdminMenu} from '@/utils/formatRoutes'
import store from './store'

router.beforeEach(async (to, from, next) => {
  if (getAccessToken()) { // 如果有token
    
    if (to.path === '/login') {
      next({ path: '/' }) // 如果是去登录页，重定向到首页
    } else {
      next() // 正常跳转
    }

  } else { // 没有token
    // await store.dispatch('LogOut').then(() => {
    //   router.push({ path: '/login' });
    // })
    if (to.path === '/login') {
      next() // 如果是去登录页，正常跳转
    } else {
      next('/login') // 否则重定向到登录页
    }
  }
})

router.afterEach(() => {
  // 这里可以添加一些在路由跳转后需要执行的代码
})
