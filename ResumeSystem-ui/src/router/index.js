import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '../views/Home.vue'
// import Home from '../views/Home.vue'
// import UserLogin from '../components/UserLogin.vue'
// import UserRegister from '../components/UserRegister.vue'
// import ChangePassword from '../components/ChangePassword.vue'
import SearchResume from '../views/SearchResume.vue'
// import SearchByStudentId from '../views/SearchByStudentId.vue'
// import SearchByResumeId from '../views/SearchByResumeId.vue'
import Resume from '../views/Resume.vue'
import ManagerLogin from '../views/ManagerLogin.vue'
import Manager from '../views/Manager.vue'
import ShortMessage from '../views/ShortMessage.vue'
import QueryByName from '../views/children/QueryByName.vue'
import QueryByStuId from "../views/children/QueryByStuId.vue"
import QueryByGroup from "../views/children/QueryByGroup.vue"
import QueryByResumeState from "../views/children/QueryByResumeState.vue"
import QueryBySMSState from "../views/children/QueryBySMSState.vue"
import AddAdmin from "../views/children/AddAdmin.vue"
import ExamineResume from "../views/children/ExamineResume.vue"
import AdminLogs from "../views/children/AdminLogs.vue"
import SMSTemplate from "../views/children/SMSTemplate.vue"
import SendSMS from "../views/children/SendSMS.vue"

Vue.use(VueRouter)

const routes = [
  {
    //默认进入/Resume，显示相应内容
    path: '',
    redirect: '/Resume',
  },
  // {
  //   path: '/Home',
  //   name: 'Home',
  //   component: Home,
  //   meta: {
  //     title: "首页|大云屋·众创空间",
  //   },
  // },
  // {
  //   path: '/UserLogin',
  //   name: 'UserLogin',
  //   component: UserLogin,
  // },
  // {
  //   path: '/UserRegister',
  //   name: 'UserRegister',
  //   component: UserRegister,
  // },
  // {
  //   path: '/ChangePassword',
  //   name: 'ChangePassword',
  //   component: ChangePassword,
  // },
  {
    path: '/SearchResume',
    name: 'SearchResume',
    component: SearchResume,
    meta: {
      title: "查询简历|大云屋·众创空间",
    },
  },
  {
    path: '/Resume',
    name: 'Resume',
    component: Resume,
    meta: {
      title: "简历编辑|大云屋·众创空间",
    },
  },
  {
    path: '/ManagerLogin',
    name: 'ManagerLogin',
    component: ManagerLogin,
    meta: {
      title: "管理员登陆|大云屋·众创空间",
    },
  },
  {
    path: '/Manager',
    name: 'Manager',
    component: Manager,
    redirect: '/QueryByGroup',
    meta: {
      title: "简历管理|大云屋·众创空间",
    },
    children: [
      {
        path: '/QueryByName',
        name: "QueryByName",
        component: QueryByName,
      },
      {
        path: '/QueryByStuId',
        name: "QueryByStuId",
        component: QueryByStuId,
      },
      {
        path: '/QueryByGroup',
        name: "QueryByGroup",
        component: QueryByGroup,
      },
      {
        path: '/QueryByResumeState',
        name: "QueryByResumeState",
        component: QueryByResumeState,
      },
      {
        path: '/QueryBySMSState',
        name: "QueryBySMSState",
        component: QueryBySMSState,
      },
      {
        path: '/AddAdmin',
        name: "AddAdmin",
        component: AddAdmin,
      },
      {
        path: '/ExamineResume',
        name: "ExamineResume",
        component: ExamineResume,
      },
      {
        path: '/AdminLogs',
        name: "AdminLogs",
        component: AdminLogs,
      },
      {
        path: '/SMSTemplate',
        name: "SMSTemplate",
        component: SMSTemplate,
      },
      {
        path: '/SendSMS',
        name: "SendSMS",
        component: SendSMS,
      },
    ]
  },
  {
    path: '/ShortMessage',
    name: 'ShortMessage',
    component: ShortMessage,
    meta: {
      title: "发送短信|大云屋·众创空间",
    },
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  /* 路由发生变化修改页面title */
  if (to.meta.title) {
    document.title = to.meta.title
  }
  // 如果目的的 UserLogin，UserRegister, Home 直接放行
  let regex = /(\/UserLogin)|(\/UserRegister)|(\/Home)|(\/ManagerLogin)|(\/ChangePassword)/i;
  if (regex.test(to.path)) {
    return next();
  }
  //获取 token 
  const token = window.localStorage.getItem("token");
  // 如果没有 token，且目的是Manager，强制跳转到管理员登陆界面，否则强制跳转到用户登陆页面
  if (!token && /\/Manager/.test(to.path)) {
    return next("/ManagerLogin");
  }
  // 有 token 的情况下，直接放行
  next();
})

export default router
