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

    if (store.state.adminMenus.length > 0) {
      // console.log(store.state.adminMenus.length,"----------------------444444");
      return;
    }else{

      getMenu().then( res=>{
        if(res.code==200){
          // 格式化路由数据
  
          const formattedRoutes = formatRoutes(res.data);
  
          // router.addRoute(formattedRoutes);
          console.log(formattedRoutes);
          // console.log(store.state.adminMenus.length,"------------------55555555555555");
  
  
          formattedRoutes.forEach(route=>{
            router.addRoute(route);
            console.log(route);
          })
        
          // 存储到 Vuex Store 的 adminMenu 中
          store.commit('setAdminMenu', formattedRoutes);
  
        }
      })
    }

  }
