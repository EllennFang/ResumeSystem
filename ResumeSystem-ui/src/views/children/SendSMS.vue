<template>
  <div id="">
    <el-card>
      <div class="Send-SMS-tips">
        <div class="text-info">发送短信</div>
        <p class="text-tip">请使用 <b>手机</b> 打开该页面</p>
        <vue-qr :text="qrHref" qid="testid"></vue-qr>
        <p>
          <router-link
            :to="{ name: 'ShortMessage' }"
            target="_blank"
            class="text-location"
            >{{ href }}</router-link
          >
        </p>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    this.qrHref = document.location.href;

    return {};
  },
  computed: {
    href() {
      return location.origin + "/ShortMessage";
    },
  },
  created() {
    // 判断是否是移动设备
    let system = {};
    let platform = navigator.platform;
    system.isWin = platform.indexOf("Win") !== -1;
    system.isMac = platform.indexOf("Mac") !== -1;
    if (!system.isWin && !system.isMac) {
      //如果是电脑
      this.$router.push({ name: "ShortMessage" });
    }
  },
};
</script>

<style lang='less' scoped>
@import "../../assets/css/global.less";
@import "../../assets/css/children/SendSMS.less";
</style>