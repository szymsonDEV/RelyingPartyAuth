import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

import axios from 'axios'
import VueAxios from 'vue-axios'

import { setupAxiosInterceptors } from './config/axios-interceptors.ts';

import { createApp } from 'vue'
import App from './App.vue'

import router from './router'

import store from './store/store'

import BootstrapVue3 from "bootstrap-vue-3";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue-3/dist/bootstrap-vue-3.css";

const app = createApp(App);
app
  .use(router)
  .use(VueAxios, axios)
  .use(store)
  .use(BootstrapVue3)


  setupAxiosInterceptors(() => console.log('Unauthorized!'));

  app.config.globalProperties.$auth = {}
  app.config.globalProperties.$auth.hasRole = function (name) {
    return store.state.account && store.state.account.user &&
    store.state.account &&
    store.state.account.authorities &&
    store.state.account.authorities.includes(name)
  }
  app.config.globalProperties.$auth.hasAnyRole = function (names) {
    return store.state.account && store.state.account.user &&
    store.state.account &&
    store.state.account.authorities &&
    store.state.account.authorities.some((x) => names.includes(x))
  }
  app.config.globalProperties.$auth.isAuthenticated = function () {
    return store.state.account
  }
  app.config.globalProperties.$auth.getUserName = function () {
    return store.state.account ? store.state.account.username : ''
  }
  app.config.globalProperties.$auth.getRegistredCredentials = function () {
    return store.state.account ? store.state.account.registredCredentials : 0
  }

  app.mount('#app');