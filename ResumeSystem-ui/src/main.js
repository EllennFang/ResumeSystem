import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './vuex/store'
import axios from 'axios'
import VueQr from "vue-qr";
import './element/element.js'
import PublicFn from './public/public.js'

// 导入全局样式表
import './assets/css/global.less'

var Less = require('less')
Vue.config.productionTip = false

// 配置请求
axios.interceptors.request.use(config => {
  let requestPath = config.url;
  let reg = /(\/stu)|(\/admin\/login)/
  if (!reg.test(requestPath)) {
    // 不是公共接口
    // config.headers.Authorization = localStorage.getItem("token");
    config.headers.token = localStorage.getItem("token");
  } else {
    // console.log("是公共接口");
  }
  return config;
})
//全局注册，使用方法为this.$axios
Vue.prototype.$axios = axios

Vue.prototype.$eventHub= Vue.prototype.$eventHub ||  new Vue()

Vue.prototype.$PublicFn = PublicFn;

Vue.use(VueQr);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
