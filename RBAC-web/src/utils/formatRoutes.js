import { userSetter } from "core-js/fn/symbol"
import { Icon } from "element-ui"
import { getMenu } from '@/api/menu'

export const formatRoutes = (routes) => {
    let fmtRoutes = []
    //测试
    // console.log('routes:', routes);

    routes.forEach(route => {
      if (route.children) {
        route.children = formatRoutes(route.children)
      }
  
      let fmtRoute = {
        path: route.url,
        component: resolve => {
          require(['@/' + route.component + '.vue'], resolve)
        },
        // name: route.name,
        // nameZh: route.nameZh,
        // iconCls: route.iconCls,
        // children: route.children
        name: route.menu_name,
        // nameZh: route.nameZh,
        iconCls: 'el-Icon-user',
        children: route.children
      }
      fmtRoutes.push(fmtRoute);
    })

    return fmtRoutes
  }

  export function initAdminMenu(router,store){
    return new Promise((resolve, reject) => {
      // 在这里执行initAdminMenu的逻辑
      if (store.state.adminMenus.length > 0) {
        resolve();
      }else{
        getMenu().then( res=>{
          if(res.code==200){
            // 格式化路由数据
            const formattedRoutes = formatRoutes(res.data);
            
            console.log("formattedRoutes:",formattedRoutes);
            formattedRoutes.forEach(route=>{
              router.addRoute(route);
              console.log("route:",route);
            })
            // 存储到 Vuex Store 的 adminMenu 中
            store.commit('setAdminMenu', formattedRoutes);
            resolve();
          }
        })
      }
    });

    

  }
