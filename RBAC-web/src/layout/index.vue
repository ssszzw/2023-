<!-- <template>
    <div>
        <div class="side-bar">
            <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
                <el-menu-item index="/" @click="$router.push('/')">
                    <i class="el-icon-house"></i>
                    <span slot="title">首页</span>
                </el-menu-item>
                <el-submenu index="">
                    <template slot="title">
                        <i class="el-icon-location"></i>
                        <span>用户管理</span>
                    </template>
                    <el-menu-item index="/rbac/user/list" @click="$router.push('/user/list')" >
                        <i class="el-icon-menu"></i>
                        <span slot="title">用户列表</span>
                    </el-menu-item>
                    <el-menu-item index="/rbac/user/add" @click="$router.push('/user/add')">
                        <i class="el-icon-setting"></i>
                        <span slot="title">添加用户</span>
                    </el-menu-item>
                </el-submenu>

                <el-submenu index="1">
                    <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>系统管理</span>
                    </template>
                    <el-menu-item-group>
                    <el-menu-item index="1-1">用户管理</el-menu-item>
                    <el-menu-item index="1-2">角色管理</el-menu-item>
                    <el-menu-item index="1-3">权限管理</el-menu-item>
                    
                    </el-menu-item-group>
                </el-submenu>
            </el-menu>

  
            <div class="main-content">
                <el-menu>
                    <el-menu-item index="/logout" @click="logout">
                        <i class="el-icon-setting"></i>
                        <span slot="title">退出</span>
                    </el-menu-item>
                </el-menu>
                <router-view/>
            </div>
        </div>
    </div>
  </template>

<script>
import { removeToken } from '@/utils/auth'

export default {
  name: "MyComponent",
  methods: {
    logout() {
      this.$confirm('确定注销并退出系统吗？', '提示').then(() => {
        removeToken() // 清除token
        this.$router.push({ path: '/login' }) // 重定向到登录页面
      }).catch(() => {

      });
    },
  },
};
</script>





  <style lang="scss" scoped>
  .side-bar {
  display: flex;
  height: 100vh;
  }
  

  .main-content {
  flex: 1;
  padding: 20px;

  }
  
  </style>
   -->


   <template>
    <div>
        <div class="side-bar">
            <!-- <el-menu :default-active="'/'" class="el-menu-admin" router mode="vertical" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"> -->
            <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
                <div style="height: 80px;"></div>
                <template v-for="(item,i) in adminMenus">
                    <!--index 没有用但是必需字段且为 string -->
                    <el-menu-item :key="item.path" :index="item.path" @click="$router.push(item.path)" v-if="!item.children">
                        <i :class="item.iconCls"></i>
                        {{ item.name }}
                    </el-menu-item>
                    <el-submenu :key="i" :index="i + ''" style="text-align: left" v-if="item.children">
                        <span slot="title" style="font-size: 17px;">
                            <i :class="item.iconCls"></i>
                            {{item.name}}
                        </span>
                        <el-menu-item v-for="child in item.children" :key="child.path" :index="child.path" @click="$router.push(child.path)">
                            <i :class="child.icon"></i>
                            {{ child.name }}
                        </el-menu-item>
                    </el-submenu>
                </template>
            </el-menu>
            <div class="main-content">
                    <el-menu>
                        <el-menu-item index="/logout" @click="logout">
                            <i class="el-icon-setting"></i>
                            <span slot="title">退出</span>
                        </el-menu-item>
                    </el-menu>
                    <router-view/>
                </div>
        </div>
    </div>
  </template>
  
  <script>
  import { removeToken } from '@/utils/auth'
  import router from '@/router';
  import store from '@/store';

    export default {
    name: 'AdminMenu',
    computed: {
        adminMenus () {
        return this.$store.state.adminMenus
        }
    },
    methods: {
        logout() {
        this.$confirm('确定注销并退出系统吗？', '提示').then(async () => {
            await store.dispatch('LogOut').then(() => {
                router.push({ path: '/login' });
            })
            removeToken() // 清除token
            // this.$router.push({ path: '/login' }) // 重定向到登录页面
        }).catch(() => {

        });
        },
    }

    }
  </script>
   <style lang="scss" scoped>
   .app-container{
    display: flex;
    height: 100vh;
   }
   .side-bar {
   display: flex;
   height: 100vh;
   }
   
   .main-content {
   flex: 1;
   padding: 20px;
   }
   
   </style>