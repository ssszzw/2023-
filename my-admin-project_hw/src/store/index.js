import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    adminMenus: [],
  },
  getters: {
  },
  mutations: {
    setAdminMenu(state, menu) {
      // 更新 adminMenu 的数据
      state.adminMenus = menu;
    }
  },
  actions: {
  },
  modules: {
  }
})
