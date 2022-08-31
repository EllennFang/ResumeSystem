<template>
  <div id="" class="manager-login">
    <div class="container">
      <div class="title">
        <div class="m-home-back">
          <router-link :to="{ name: 'Home' }">首页</router-link>
        </div>
        <div>管理员登陆</div>
        <div></div>
      </div>
      <div class="home-back">
        <router-link :to="{ name: 'Home' }">返回首页</router-link>
      </div>
      <div class="login">
        <el-form
          :model="managerLoginData"
          :rules="managerLoginRules"
          ref="managerLoginDataRef"
          :label-width="formLabelInfo.labelWidth"
        >
          <el-form-item :label="formLabelInfo.username" prop="username">
            <!-- 账号 -->
            <el-input
              v-model="managerLoginData.username"
              @keyup.enter.native="managerLogin"
              placeholder="请输入账号"
              maxlength="15"
              autofocus
            ></el-input>
          </el-form-item>
          <!-- 密码 -->
          <el-form-item :label="formLabelInfo.password" prop="password">
            <el-input
              v-model.trim="managerLoginData.password"
              @keyup.enter.native="managerLogin"
              placeholder="请输入密码"
              maxlength="15"
              type="password"
              show-password
              autofocus
            ></el-input>
          </el-form-item>
          <!-- 验证码 -->
          <div class="code-item">
            <el-form-item
              :label="formLabelInfo.codeKey"
              class="code-item"
              prop="codeKeyInput"
            >
              <el-input
                v-model.trim="managerLoginData.codeKeyInput"
                @keyup.enter.native="managerLogin"
                placeholder="请输入验证码"
                maxlength="4"
              ></el-input>
            </el-form-item>
            <!-- 验证码图片区域 -->
            <div class="code-key">
              <div class="remember-detail">
                <el-checkbox-group v-model="checkboxType">
                  <el-checkbox
                    label="记住密码"
                    name="checkboxType"
                  ></el-checkbox>
                </el-checkbox-group>
              </div>
              <div class="code-img">
                <img
                  :src="codeData.imgString"
                  @click="changeCodeImg"
                  alt="验证码"
                />
                <span>
                  <a href="javascript: void(0);" @click="changeCodeImg"
                    >看不清，换一张
                  </a>
                </span>
              </div>
            </div>
          </div>
          <div class="form-button">
            <!-- <el-button @click="resetmanagerLogin('managerLoginDataRef')"
              >重置</el-button
            > -->
            <el-button type="primary" @click="managerLogin">登陆</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { checkResume_api } from "../network/student";
import { login } from "../network/admin";

export default {
  name: "",
  data() {
    return {
      formLabelInfo: {
        username: "账号",
        password: "密码",
        codeKey: "验证码",
        labelWidth: "70px",
      },
      managerLoginData: {
        username: "",
        password: "",
        codeKeyInput: "",
      },
      codeKey: "",
      codeData: {},
      // 账号密码验证码表单的验证规则对象
      managerLoginRules: {
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            pattern: /^([0-9]|[a-z]|[A-Z])*$/i,
            message: "请输入正确的账号",
            trigger: "blur",
          },
          {
            pattern: /^([0-9]|[a-z]|[A-Z])*$/i,
            message: "请输入正确的账号",
            trigger: "change",
          },
          { min: 4, message: "请输入正确的账号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            pattern: /^([0-9]|[a-z]|[\*]|[\.])*$/i,
            message: "请输入正确的账号",
            trigger: "change",
          },
          { min: 6, message: "请输入正确的密码", trigger: "blur" },
        ],
        codeKeyInput: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          { min: 4, max: 4, message: "请输入正确的验证码", trigger: "blur" },
          {
            pattern: /^([a-z]|\d)*$/i,
            message: "请输入正确的验证码",
            trigger: "blur",
          },
          {
            pattern: /^([a-z]|\d)*$/i,
            message: "请输入正确的验证码",
            trigger: "change",
          },
        ],
      },
      checkboxType: false,
    };
  },
  methods: {
    // 窗口宽度发送变化
    handleResize() {
      if (document.body.getBoundingClientRect().width < 850) {
        // 宽度小于850px
        console.log("宽度小于850px");
        this.formLabelInfo.username = "";
        this.formLabelInfo.password = "";
        this.formLabelInfo.codeKey = "";
        this.formLabelInfo.labelWidth = "";
      } else {
        this.formLabelInfo.username = "账号";
        this.formLabelInfo.password = "密码";
        this.formLabelInfo.codeKey = "验证码";
        this.formLabelInfo.labelWidth = "70px";
      }
    },
    // 更换验证码
    changeCodeImg() {
      // 避免频繁发送请求
      if (this.$PublicFn.frequentlyCount(this)) return;
      // 发送请求获取验证码
      // 发送get请求
      checkResume_api
        .getCode({ codeKey: this.codeKey })
        .then((res) => {
          if (res.state) {
            this.codeData = res;
            this.codeKey = res.codeKey;
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("验证码获取失败");
        });
    },
    // 管理员点击登陆
    async managerLogin() {
      let loginValid;
      this.$refs["managerLoginDataRef"].validate(
        (valid) => (loginValid = valid)
      );
      if (!loginValid) {
        //提示简历提交失败，输入完整信息
        return false;
      }
      // 避免频繁发送请求
      if (this.$PublicFn.frequentlyCount(this)) return;
      // 添加登陆数据至 formData
      let managerLoginData = this.managerLoginData;
      let formData = new FormData();
      formData.append("username", managerLoginData.username);
      formData.append("password", managerLoginData.password);
      formData.append("codeKey", this.codeKey);
      formData.append("codeKeyInput", managerLoginData.codeKeyInput);
      // 发送post请求
      let res = await login(formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
      if (res.state) {
        this.$message.success("登陆成功");
        // 将 token 和 role 存储到 localstorage
        let token = res.token;
        let role = res.role;
        localStorage.setItem("token", token);
        localStorage.setItem("role", role);
        localStorage.setItem("username", this.managerLoginData.username); // 记住账号
        // 记住密码
        if (this.checkboxType) {
          // 选择了记住密码
          localStorage.setItem("password", this.managerLoginData.password);
        } else {
          // 没有选择记住密码
          localStorage.removeItem("password"); // 删除缓存中的密码
        }
        this.$router.push({ name: "Manager" }).catch((routerRes) => {
          console.log("路由跳转失败");
          console.log(routerRes);
        });
      } else {
        // 登陆失败错误
        this.changeCodeImg();
        this.$message.error(res.msg);
      }
    },
  },
  created() {
    let errorMsg = localStorage.getItem("errorMsg");
    if (errorMsg != undefined) {
      localStorage.removeItem("errorMsg");
      this.$message.error(errorMsg);
    }
  },
  beforeMount() {
    window.addEventListener("resize", this.handleResize);
  },
  mounted() {
    // 验证是否有 token 且是否过期
    let token = localStorage.getItem("token");
    let role = localStorage.getItem("role");
    if (token && role) {
      // 已有 token 和 role，直接跳转到后台管理页面
      this.$router.push({ name: "Manager" }).catch((res) => {
        console.log("路由跳转失败");
        console.log(res);
      });
    }
    // 自动填写账号和密码
    let username = localStorage.getItem("username");
    let password = localStorage.getItem("password");
    if (username != "undefined" && username) {
      this.managerLoginData.username = username;
    }
    if (password != "undefined" && password) {
      this.managerLoginData.password = password;
      this.checkboxType = true;
    }
    // 判断窗口宽度情况
    this.handleResize();
    // 加载验证码
    this.$nextTick(async () => {
      //整个视图都渲染完毕才运行
      // 发送请求获取验证码
      // 发送get请求
      checkResume_api
        .getCode({ codeKey: this.codeKey })
        .then((res) => {
          if (res.state) {
            this.codeData = res;
            this.codeKey = res.codeKey;
          } else {
            this.$message.error(res.msg);
          }
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("验证码获取失败");
        });
    });
  },
  beforeDestroy() {
    window.removeEventListener("resize", this.handleResize);
  },
};
</script>

<style lang='less' scoped>
@media screen and (min-width: 850px) {
  @import "../assets/css/ManagerLogin.less";
}
@media screen and (max-width: 850px) {
  @import "../assets/css/mobile/ManagerLogin.less";
}
</style>