import { createStore } from 'vuex'
import axios from 'axios'
import router from './../router'

const store = createStore({
  state () {
    return {
      account: null
    }
  },
  mutations: {
    setAccount (state,data) {
      if(data) {
        state.account = data
      } else {
        state.account = null
      }
    }
  },
  actions: {
    loadAccount ({ commit }) {
      commit('setAccount',null)
      return axios.get('api/account')
        .then(response => {
          const account = response.data;
          if (account) {
            commit('setAccount', account);
            return account
          }
        })
        .catch(() => {
          //commit('logout');
          router.push('/');
        });
    }
  },
  getters: {
    account (state) {
      return state.account;
    },
    userName (state) {
      return state.account;
    }
  }
})


export default store