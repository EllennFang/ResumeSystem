<template>
  <div id="" class="search-container">
    <div class="container">
      <div class="title-line">
        <span class="tit">查询简历</span>
      </div>
      <div class="content">
        <!-- 左侧二维码 -->
        <div class="qr-code">
          <div class="qrcode-info">
            <img src="../assets/images/QRcode_qq_1.jpg" alt="" />
            <div class="group-info">
              <p class="info">群名称：大云屋咨询1群</p>
              <p class="info">群 号：451841367</p>
              <p class="info">扫描二维码加入大云屋咨询群</p>
              <el-popover placement="top" width="200" trigger="click">
                <div class="popover-more-info">
                  <div class="popover-left">
                    <div class="qrcode-left">
                      <img
                        src="../assets/images/QRcode_gongzhonghao.png"
                        alt="公众号二维码"
                      />
                    </div>
                    <div class="qrcode-right">
                      <img
                        src="../assets/images/QRcode_qq_1.jpg"
                        alt="咨询1群二维码"
                      /><img
                        src="../assets/images/QRcode_qq_2.png"
                        alt="咨询2群二维码"
                      />
                    </div>
                  </div>
                  <div class="popover-right">
                    <ul>
                      <li>地址：广东工业大学-大学城校区工学一号馆401室</li>
                      <li>
                        网址：<a
                          href="https://dyw.gdut.edu.cn/"
                          target="blank"
                          style="color: #409eff"
                          >https://dyw.gdut.edu.cn/</a
                        >
                      </li>
                      <li>微信公众号：大云屋众创空间</li>
                      <li>Email：cloudhouse_401@163.com</li>
                      <li>QQ咨询群：451841367 307209526</li>
                    </ul>
                  </div>
                </div>
                <span class="more-info" slot="reference">更多联系方式</span>
                <!-- <el-button slot="reference">click 激活</el-button> -->
              </el-popover>
            </div>
          </div>
          <div class="mobile-qrcode-info">
            <div class="popover-left">
              <div class="qrcode-left">
                <img
                  src="../assets/images/QRcode_gongzhonghao.png"
                  alt="公众号二维码"
                />
                <p>公众号二维码</p>
              </div>
              <div class="qrcode-right">
                <div>
                  <img
                    src="../assets/images/QRcode_qq_1.jpg"
                    alt="咨询1群二维码"
                  />
                  <p>咨询1群二维码</p>
                </div>
                <div>
                  <img
                    src="../assets/images/QRcode_qq_2.png"
                    alt="咨询2群二维码"
                  />
                  <p>咨询2群二维码</p>
                </div>
              </div>
            </div>
            <div class="popover-right">
              <ul>
                <li>地址：广东工业大学-大学城校区工学一号馆401室</li>
                <li>
                  网址：<a
                    href="https://dyw.gdut.edu.cn/"
                    target="blank"
                    style="color: #409eff"
                    >https://dyw.gdut.edu.cn/</a
                  >
                </li>
                <li>微信公众号：大云屋众创空间</li>
                <li>Email：cloudhouse_401@163.com</li>
                <li>QQ咨询群：451841367 307209526</li>
              </ul>
            </div>
          </div>
        </div>
        <!-- 中间竖直分割线 -->
        <div class="line"></div>
        <!-- 右侧表单区域 -->
        <div class="search-box">
          <div class="type-tab">
            <span
              :class="{ active: searchByStudentId }"
              @click="changeTab('resumeIdDataRef')"
              >学号查询</span
            >
            <span
              :class="{ active: !searchByStudentId }"
              @click="changeTab('studentIdDataRef')"
              >凭证码查询</span
            >
          </div>
          <!-- 通过学号查询简历 -->
          <div class="search" v-if="searchByStudentId">
            <el-form
              :model="studentIdData"
              :rules="studentIdRules"
              ref="studentIdDataRef"
              key="studentId"
            >
              <!-- 姓名 -->
              <el-form-item prop="name">
                <el-input
                  v-model="studentIdData.name"
                  @keyup.enter="searchResume()"
                  placeholder="请输入姓名"
                  maxlength="20"
                  autofocus
                  key="1"
                ></el-input>
              </el-form-item>
              <!-- 学号 -->
              <el-form-item prop="studentId">
                <el-input
                  v-model.trim="studentIdData.studentId"
                  @keyup.enter="searchResume()"
                  placeholder="请输入学号"
                  maxlength="10"
                  key="2"
                ></el-input>
              </el-form-item>
              <!-- 验证码 -->
              <el-form-item class="code-item" prop="rcodeKeyInput">
                <el-input
                  v-model.trim="studentIdData.rcodeKeyInput"
                  @keyup.enter="searchResume()"
                  placeholder="请输入验证码"
                  maxlength="4"
                  key="3"
                ></el-input>
              </el-form-item>
              <!-- 验证码图片 -->
              <div class="code-key">
                <div class="resume">
                  <router-link :to="{ name: 'Resume' }"
                    >还未填写简历？</router-link
                  >
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
              <div class="form-button">
                <el-button
                  @click="resetSearchResume('studentIdDataRef')"
                  style="width: 45%"
                  >重置</el-button
                >
                <el-button
                  type="primary"
                  @click="searchResume('studentIdDataRef')"
                  style="width: 45%"
                  >查询</el-button
                >
              </div>
            </el-form>
          </div>
          <!-- 通过凭证码查询简历 -->
          <div class="search" v-else>
            <el-form
              :model="resumeIdData"
              :rules="resumeIdRules"
              ref="resumeIdDataRef"
              key="resumeId"
            >
              <!-- 简历凭证码 -->
              <el-form-item prop="resumeId">
                <el-input
                  v-model.trim="resumeIdData.resumeId"
                  @keyup.enter="searchResume()"
                  placeholder="请输入凭证码"
                  maxlength="50"
                  autofocus
                  key="4"
                ></el-input>
              </el-form-item>
              <!-- 验证码 -->
              <el-form-item class="code-item" prop="codeKeyInput">
                <el-input
                  v-model.trim="resumeIdData.codeKeyInput"
                  @keyup.enter="searchResume()"
                  placeholder="请输入验证码"
                  maxlength="4"
                  key="5"
                ></el-input>
              </el-form-item>
              <!-- 验证码图片 -->
              <div class="code-key">
                <div class="resume">
                  <router-link :to="{ name: 'Resume' }"
                    >还未填写简历？</router-link
                  >
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
              <div class="form-button">
                <el-button @click="resetSearchResume('resumeIdDataRef')"
                  >重置</el-button
                >
                <el-button
                  type="primary"
                  @click="searchResume('resumeIdDataRef')"
                  >查询</el-button
                >
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { checkResume_api } from "../network/student";
export default {
  name: "",
  data() {
    return {
      searchByStudentId: true, // 控制 tab 栏显示
      // 学号表单的验证规则对象
      studentIdRules: {
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          {
            pattern: /^([\u4E00-\u9FA5]|[a-z])*$/i,
            message: "请输入正确的姓名",
            trigger: "blur",
          },
          {
            pattern: /^([\u4E00-\u9FA5]|[a-z])*$/i,
            message: "请输入正确的姓名",
            trigger: "change",
          },
        ],
        studentId: [
          { required: true, message: "请输入学号", trigger: "blur" },
          { min: 10, max: 10, message: "请输入正确的学号", trigger: "blur" },
          {
            pattern: /^[3][0-9]{9}$/,
            message: "请输入正确的学号",
            trigger: "blur",
          },
          {
            pattern: /^[0-9]*$/,
            message: "请输入正确的学号",
            trigger: "change",
          },
        ],
        rcodeKeyInput: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          // { min: 4, max: 4, message: "请输入正确的验证码", trigger: "blur" },
          // {
          //   pattern: /^([a-z]|\d)*$/i,
          //   message: "请输入正确的验证码",
          //   trigger: "blur",
          // },
          {
            pattern: /^([a-z]|\d){4}$/i,
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
      // 凭证吗表单的验证规则对象
      resumeIdRules: {
        resumeId: [
          { required: true, message: "请输入凭证码", trigger: "change" },
          { required: true, message: "请输入凭证码", trigger: "blur" },
        ],
        codeKeyInput: [
          { required: true, message: "请输入验证码", trigger: "blur" },
          // { min: 4, max: 4, message: "请输入正确的验证码", trigger: "blur" },
          // {
          //   pattern: /^([a-z]|\d)*$/i,
          //   message: "请输入正确的验证码",
          //   trigger: "blur",
          // },
          {
            pattern: /^([a-z]|\d){4}$/i,
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
      // 学号表单数据
      studentIdData: {
        name: "",
        studentId: "",
        rcodeKeyInput: "",
      },
      // 凭证吗表单数据
      resumeIdData: {
        resumeId: "",
        codeKeyInput: "",
      },
      codeKey: "",
      codeData: {}, // 请求得到的验证码数据
    };
  },
  methods: {
    // 更改查询方式
    changeTab(formName) {
      this.$refs[formName].resetFields();
      if (formName == "resumeIdDataRef") {
        this.searchByStudentId = true;
      } else {
        this.searchByStudentId = false;
      }

      // console.log(elem);
      // console.log(typeof(elem));
      // for (let item in elem) {
      //   console.log(item);
      // }
    },
    // 更换验证码
    async changeCodeImg() {
      try {
        let res = await checkResume_api.getCode({ codeKey: this.codeKey });
        if (res.state) {
          this.codeData = res;
        } else {
          this.$message.error(res.msg);
        }
      } catch (err) {
        this.$message.error("change CodeImg error");
      }
    },
    // 点击重置，进行表单
    resetSearchResume(formName) {
      this.$refs[formName].resetFields();
    },
    // 点击查询，验证并提交表单
    async searchResume(formName) {
      let formRulesState = false;
      this.$refs[formName].validate((valid) => {
        formRulesState = valid;
      });
      if (!formRulesState) return;
      try {
        let res = {};
        if (formName == "studentIdDataRef") {
          // 通过学号姓名查询简历信息
          let studentIdData = this.studentIdData;
          res = await checkResume_api.checkByStudentId({
            studentId: studentIdData.studentId,
            name: studentIdData.name,
            codeKeyInput: studentIdData.rcodeKeyInput,
            codeKey: this.codeData.codeKey,
          });
        } else {
          // 通过凭证吗查询简历信息
          let resumeIdData = this.resumeIdData;
          res = await checkResume_api.checkByResumeId({
            resumeId: resumeIdData.resumeId,
            codeKey: this.codeData.codeKey,
            codeKeyInput: resumeIdData.codeKeyInput,
          });
        }
        if (!res.state) {
          this.$message.error(res.msg);
          this.changeCodeImg();
          return;
        }
        // 查询简历成功
        sessionStorage.setItem("searchResumeData", "searchResumeData");
        res.data.intensionGroup = res.data.intensionGroup.split(",");
        this.$store.state.resumeData = res.data;
        this.$message.success(res.msg);
        this.$router.push({ name: "Resume" });
      } catch (err) {
        this.$message.error("check resume error");
      }
      // let params = "";
      // let url = "";
      // if (formName == "studentIdDataRef") {
      //   // 通过学号 + 姓名查询简历信息
      //   let studentIdData = this.studentIdData;
      //   url = "stu/checkByStudentId?";
      //   params =
      //     "studentId=" +
      //     studentIdData.studentId +
      //     "&name=" +
      //     studentIdData.name +
      //     "&codeKey=" +
      //     this.codeData.codeKey +
      //     "&codeKeyInput=" +
      //     studentIdData.rcodeKeyInput;
      // } else {
      //   // 通过凭证吗查询简历信息
      //   let studentIdData = this.resumeIdData;
      //   url = "stu/checkByResumeId?";
      //   params =
      // "resumeId=" +
      // studentIdData.resumeId +
      // "&codeKey=" +
      // this.codeData.codeKey +
      // "&codeKeyInput=" +
      // studentIdData.codeKeyInput;
      // }

      // this.$axios({
      //   methods: "get",
      //   url: "http://8.129.163.145:10012/" + url + params,
      // }).then((res) => {
      //   if (res.status != 200) {
      //     // 请求失败
      //     this.$message.error(res.msg);
      //     this.changeCodeImg();
      //     return;
      //   } else if (!res.data.state) {
      //     // 查询失败
      //     this.$message.error(res.data.msg);
      //     this.changeCodeImg();
      //     return;
      //   }
      // // 查询简历成功
      // sessionStorage.setItem("searchResumeData", "searchResumeData");
      // res.data.data.intensionGroup = res.data.data.intensionGroup.split(",");
      // this.$store.state.resumeData = res.data.data;
      // this.$message.success(res.data.msg);
      // this.$router.push({ name: "Resume" });
      // });
    },
  },
  created() {
    this.changeCodeImg();
  },
};
</script>

<style lang='less' scoped>
@import "../assets/css/SearchResume.less";
</style>