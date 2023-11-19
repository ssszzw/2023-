import Vue from 'vue'
import Vuex from 'vuex'

import { logout } from '@/api/login';
import { removeToken } from '@/utils/auth';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    adminMenus: [],
    roles: [],
    permissions: []
  },
  getters: {
  },
  mutations: {
    setAdminMenu(state, menu) {
      // 更新 adminMenu 的数据
      state.adminMenus = menu;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },
  actions: {
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  },
  modules: {
  }
})
