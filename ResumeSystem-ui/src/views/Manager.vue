<template>
  <div id="" class="id-manager">
    <!-- <div class="login"></div> -->
    <el-container class="manager-container">
      <el-header>
        <span>
          <router-link :to="{ name: 'Manager' }">
            <div class="nav-logo">
              <img src="../assets/images/cloudhouselogo.jpg" alt="cloudhouse-logo" />
            </div>
          </router-link>
        </span>
        <span class="logout-item">
          <el-button class="logout-button" @click="logout">退出登陆</el-button>
        </span>
      </el-header>
      <el-container>
        <el-menu :collapse="isCollapse" router :default-active="activePath">
          <el-menu-item index="/Manager">
            <i class="el-icon-menu"></i>
            <span slot="title">主页</span>
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span slot="title">简历审核</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/QueryByGroup">意向组别查询</el-menu-item>
              <el-menu-item index="/QueryByName">姓名查询</el-menu-item>
              <el-menu-item index="/QueryByStuId">学号查询</el-menu-item>
              <el-menu-item index="/QueryByResumeState"
                >简历状态查询</el-menu-item
              >
              <el-menu-item index="/QueryBySMSState"
                >短信发送状态查询</el-menu-item
              >
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="/AddAdmin" v-if="role == 'root'">
            <i class="el-icon-menu"></i>
            <span slot="title">添加管理员</span>
          </el-menu-item>
          <el-menu-item index="/SMSTemplate">
            <i class="el-icon-setting"></i>
            <span slot="title">短信模板</span>
          </el-menu-item>
          <el-menu-item index="/SendSMS">
            <i class="el-icon-setting"></i>
            <span slot="title">发送短信</span>
          </el-menu-item>
          <el-menu-item index="/AdminLogs">
            <i class="el-icon-document"></i>
            <span slot="title">查看日志</span>
          </el-menu-item>
          <div class="zoom" @click="isCollapse = !isCollapse">
            <i class="el-icon-arrow-left" v-if="!isCollapse"></i>
            <i class="el-icon-arrow-right" v-else></i>
          </div>
        </el-menu>
        <el-main>
          <router-view
            @transmitResumeInfo="PtransmitResumeInfo"
            :resumeInfo="resumeInfo"
          ></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {queryResumeMethods} from '../network/admin'
export default {
  data() {
    return {
      role: "", // 当前管理员权限
      isCollapse: false, // 左侧菜单是否收缩
      activePath: "/QueryByGroup", // 菜单项路径选中
      name: "李四",
      intensionGroup: "1",
      resumeInfo: {},
    };
  },
  methods: {
    // 用于接收子组件发送的事件和参数
    PtransmitResumeInfo(data) {
      this.resumeInfo = data;
    },
    // 退出登陆
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      this.$router.push({ name: "ManagerLogin" }).catch((res) => {
        console.log("路由跳转失败");
        console.log(res);
      });
    },
  },
  // 路由导航守卫
  beforeRouteEnter(to, from, next) {
    window.sessionStorage.setItem("activePath", to.fullPath);
    next();
  },
  beforeCreate() {
    // 验证是否有 token 和 role 且是否过期
    let token = localStorage.getItem("token");
    let role = localStorage.getItem("role");
    if (!token && !role) {
      // 没有有 token 或 role，直接跳转到管理员登陆页面
      this.$router.push({ name: "ManagerLogin" }).catch((res) => {
        console.log("路由跳转失败");
        console.log(res);
      });
    }
  },
  created() {
    this.role = window.localStorage.getItem("role");
    this.activePath = window.sessionStorage.getItem("activePath");
  },
};
</script>

<style lang='less' scoped>
@import "../assets/css/Manager.less";
</style>