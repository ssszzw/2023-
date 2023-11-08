import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

import Layout from '@/layout'

Vue.use(VueRouter)

// 防止连续点击多次路由报错
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}


const routes = [
  // {
  //   path: '/',
  //   component: Layout, // 修改为 Layout 组件
  //   children: [
  //     {
  //       path: '',
  //       component: () => import( '../views/HomeView.vue'),
  //       name: 'home'
  //     }
  //   ]
  // },
  // {
  //   path: '/user',
  //   component: Layout,
  //   children: [{
  //       path: 'list',
  //       component:() => import( '@/views/User/List.vue')
  //     },
  //     {
  //       path: 'add',
  //       component:() => import( '@/views/User/Add.vue')
  //     }
  //   ]
  // },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
