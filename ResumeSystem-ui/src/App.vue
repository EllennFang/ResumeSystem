<!--
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-03-27 23:50:50
 * @LastEditors: Youngletter
 * @LastEditTime: 2021-08-31 21:28:21
-->
<template>
  <div id="app">
    <!-- <router-link to="Resume">按钮</router-link> -->
    <router-view></router-view>
  </div>
</template>

<script>
// import Resume from './views/Resume.vue'
export default {
  name: "",
  data() {
    return {};
  },
  mounted() {
    document.getElementById("app").style.display = "block";
    document.getElementById("loading").style.display = "none";
  }
};
</script>

<style scoped>
</style>