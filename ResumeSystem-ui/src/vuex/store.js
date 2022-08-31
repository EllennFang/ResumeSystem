import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  count: 0,
  resumeData: {}
}

export default new Vuex.Store({
  state
})