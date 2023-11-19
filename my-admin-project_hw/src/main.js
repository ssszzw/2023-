import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

import './permission' 
import {getAccessToken} from '@/utils/auth';
import {formatRoutes} from '@/utils/formatRoutes'
import {getMenu} from '@/api/menu'


Vue.use(ElementUI);


Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),

  created() {
    // 在 created 钩子中重新初始化状态的逻辑
    const isLoggedIn = getAccessToken();

    if (isLoggedIn) {
      // 执行恢复登录状态的逻辑

      // 示例：假设你需要在重新初始化后执行的操作
      getMenu().then(menuRes => {
        if (menuRes.code === 200) {
          const formattedRoutes = formatRoutes(menuRes.data);
          formattedRoutes.forEach(route => {
            router.addRoute(route);
          });

          store.commit('setAdminMenu', formattedRoutes);
        } else {
          // 处理获取菜单信息失败的情况
          console.error('获取菜单信息失败');
        }
      }).catch(() => {
        // 处理异常情况
        console.error('菜单获取发生错误，请重试');
      });
    }
  }


}).$mount('#app')


