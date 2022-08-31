<!--
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-03-27 23:50:50
 * @LastEditors: Youngletter
 * @LastEditTime: 2021-08-30 20:22:50
-->
<template>
  <div id="" class="search-container">
    <header>
      <div class="header-nav-container">
        <div class="nav-logo"><img src="http://dayunwu.manu.com.cn/wp-content/uploads/2019/02/%E6%9C%AA%E6%A0%87%E9%A2%98-1_%E7%94%BB%E6%9D%BF-1_gaitubao_com_60x60.png" alt=""></div>
        <div class="nav-main">
          <div class="signin">
            <ul>
              <li><router-link :to="{ name: 'Home' }">主页</router-link></li>
              <!-- <li><router-link :to="{ name: 'UserLogin' }">登录</router-link></li> -->
              <li>
                <router-link :to="{ name: 'SearchResume' }">查找简历</router-link>
              </li>
              <li>
                <router-link :to="{ name: 'ManagerLogin' }">管理员登录</router-link>
              </li>
            </ul>
          </div>
        </div>
        <div class="resume-nav"><router-link :to="{ name: 'Resume' }">填写简历</router-link></div>
      </div>
    </header>
    <!-- <div class="login-container">
      <h1>主页</h1>
      <router-link :to="{ name: 'UserLogin' }">用户登陆</router-link><br />
      <router-link :to="{ name: 'UserRegister' }">用户注册</router-link><br />
      <router-link :to="{ name: 'Resume' }">填写简历</router-link><br />
      <router-link :to="{ name: 'ManagerLogin' }">管理员登陆</router-link><br />
    </div> -->
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {};
  },
};
</script>

<style lang="less" scoped>
@import "../assets/css/Home.less";
</style>