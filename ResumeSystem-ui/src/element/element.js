import Vue from 'vue'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import { Button, Form, FormItem, Input } from 'element-ui'

ElementUI.TableColumn.props.showOverflowTooltip = { type: Boolean, default: true }// 让Table的列的 show-overflow-tooltip 默认为true

Vue.use(ElementUI)
Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)