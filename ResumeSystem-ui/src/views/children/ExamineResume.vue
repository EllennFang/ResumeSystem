<!--
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-03-27 23:50:50
 * @LastEditors: Youngletter
 * @LastEditTime: 2021-09-20 10:32:25
-->
<template>
  <div class="detail-box">
    <!-- 返回功能 -->
    <el-page-header @back="goBack" content="简历详情"> </el-page-header>
    <!-- 基础信息栏 -->
    <div class="basic-info">
      <h2>
        <i class="el-icon-user-solid"></i>
        基础信息
      </h2>
      <el-row :gutter="4">
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">姓名</span>
            <span class="bold" >：</span>
            <span>{{ info.name }}</span>
          </div>
        </el-col>
        <el-col :span="10">
          <span class="align bold">性别</span><span class="bold" >：</span>
          <span>{{ info.sex }}</span>
        </el-col>
      </el-row>
      <el-row :gutter="4">
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">学院</span><span class="bold" >：</span>
            <span>{{ info.department }}</span>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">年级专业</span><span class="bold" >：</span>
            <span>{{ fullProfession }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="4">
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">学号</span><span class="bold" >：</span>
            <span>{{ info.studentId }}</span>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">手机号码</span><span class="bold" >：</span>
            <span>{{ info.phone }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="4">
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">宿舍号</span><span class="bold" >：</span>
            <span>{{ info.address }}</span>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">邮箱地址</span><span class="bold" >：</span>
            <span>{{ info.mailaddress }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="4">
        <el-col :span="10">
          <div class="text-content">
            <span class="align bold">QQ号</span><span class="bold" >：</span>
            <span>{{ info.qq }}</span>
          </div>
        </el-col></el-row
      >
    </div>
    <!-- 报名信息栏 -->
    <div class="signup-info">
      <h2>
        <i class="el-icon-s-order"></i>
        报名信息
      </h2>
      <el-row :gutter="4">
        <el-col :span="10">
          <div class="text-content">
            <span class="bold">意向组别：</span>
            <span>{{ intensionGroups }}</span>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="text-content">
            <span class="bold" >是否接受调剂：</span>
            <span>{{ info.adjustment }}</span>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >报名原因：</span>
          <span class="context" v-if="info.joinReason">{{
            info.joinReason
          }}</span>
          <span v-else>无</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >未来规划：</span>
          <span class="context" v-if="info.futurePlanning">{{
            info.futurePlanning
          }}</span>
          <span v-else>无</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >自身优势：</span>
          <span class="context" v-if="info.advantage">{{
            info.advantage
          }}</span>
          <span v-else>无</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >自身劣势：</span>
          <span class="context" v-if="info.disadvantage">{{
            info.disadvantage
          }}</span>
          <span v-else>无</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >工作经历：</span>
          <div
            v-for="(item, index) in workExperience"
            :key="index"
            class="text-content"
          >
            <el-col :span="8" class="work-info">{{
              item.associationName
            }}</el-col>
            <el-col :span="8" class="work-info">{{
              item.workDate[0] + "~" + item.workDate[1]
            }}</el-col>
            <el-col :span="8" class="work-info">{{ item.postName }}</el-col>
            <div class="context" style="text-indent: 2em">
              {{ item.job }}
            </div>
          </div>
        </div>
      </el-row>
    </div>
    <!-- 其他信息栏 -->
    <div class="other-info">
      <h2>
        <i class="el-icon-notebook-1"></i>
        其他信息
      </h2>
      <el-row>
        <div class="text-content">
          <span class="bold" >可支配时间：</span>
          <span>{{ info.disposableTime }}</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >在校奖励：</span>
          <span class="context" v-if="info.schoolAward">{{
            info.schoolAward
          }}</span>
          <span v-else>无</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >自我介绍：</span>
          <span class="context" v-if="info.selfIntroduction">{{
            info.selfIntroduction
          }}</span>
          <span v-else>无</span>
        </div>
      </el-row>
      <el-row>
        <div class="text-content">
          <span class="bold" >附件信息：</span>
          <button @click="download()" v-if="info.path != null">
            <i class="el-icon-download"></i>
            下载文件
          </button>
          <span v-else>无上传附件</span>
        </div>
      </el-row>
    </div>
    <!-- 评价修改栏 -->
    <div class="asssess-info">
      <h2>
        <i class="el-icon-s-comment"></i>
        评价信息
      </h2>
      <el-row :gutter="0">
        <div class="text-content">
          <el-col style="vertical-align: top" :span="2" class="bold">评语：</el-col>
          <el-col :span="22">
            <textarea
              class="context"
              v-if="!info.comment"
              placeholder="请输入评语"
            ></textarea>
            <span v-else class="context comment" @dblclick="modify">{{
              info.comment
            }}</span>
          </el-col>
        </div>
      </el-row>
      <el-row class="result">
        <el-col :span="14">
          <button @click="examine">筛选结果</button>
        </el-col>
        <el-col :span="6">
          <button @click="interview">面试结果</button>
        </el-col>
      </el-row>
      <el-dialog
        title="结果"
        :visible.sync="tipDialogVisible"
        :append-to-body="true"
        width="30%"
        class="el-dialog-tip"
      >
        <span v-if="status == true">是否通过简历筛选？</span>
        <span v-else>是否通过面试？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="fail">不 通 过</el-button>
          <el-button type="primary" @click="succeed">通 过</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { ModifyResume } from '../../network/admin'

export default {
  props: ["resumeInfo"],
  data() {
    return {
      // 工作经历
      workExperience: eval("(" + this.resumeInfo.workExperience + ")"),
      // 对话框状态
      tipDialogVisible: false,
      // 展示状态,true为展示筛选,false为展示面试
      status: true,
      // 将从父组件传递过来的数据保存在data中,使用watch,实时让info等于props传递过来的值
      info: {},
    };
  },
  computed: {
    // 年级专业拼接
    fullProfession() {
      return this.info.grade + this.info.major;
    },
    // 意向组别拼接
    intensionGroups() {
      let group = this.info.intensionGroup;
      let str = "";
      for (let i = 0; i < group.length; i++) {
        str += group[i] + "，";
      }
      return str.slice(0, str.length - 1);
    },
  },
  methods: {
    // 返回原来界面
    goBack() {
      // 在返回上一页面前,检测textarea是否有,如果有是否有值,如果有值,发送请求修改,如果没有就不发送,但最后都要跳转
      const text = document.querySelector("textarea");
      if ( text && text.value != "") {
        let formData = new FormData();
        formData.append("resumeId", this.info.resumeId);
        formData.append("comment", text.value);
        // 发送post请求
        ModifyResume.saveComment(formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          }
        })
          .then((res) => {
            if (!res.state) {
              // 添加失败,不返回原来界面可继续请求
              this.$message.error("评语保存失败");
            } else {
              this.$message.success("评语保存成功");
              this.$router.go(-1);
            }
          })
          .catch((err) => {
             console.log(err);
            // 如果是因为无法发送请求,不返回原来界面可继续请求
            this.$message.error("评语保存失败");
          });
      } else {
        this.$router.go(-1);
      }
    },
    // 文件下载
    download() {
      let resumeId = this.info.resumeId;
      let link = document.createElement("a");
      link.style.display = "none";
      link.href =
        "http://8.129.163.145:10012/stu/download?resumeId=" + resumeId;
      document.body.appendChild(link);
      link.click();
    },
    // 筛选结果跳转函数
    examine() {
      this.status = true;
      this.tipDialogVisible = true;
    },
    // 面试结果跳转函数
    interview() {
      this.status = false;
      this.tipDialogVisible = true;
    },
    // 失败函数
    fail() {
      let formData = new FormData();
      formData.append("resumeId", this.info.resumeId);
      // 如果是简历筛选不通过
      if (this.status == true) {
        formData.append("resumeState", "4");
      } else {
        // 如果是面试不通过
        formData.append("resumeState", "3");
      }
      // 发送put请求
      ModifyResume.updateResumeState(formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        }
      })
        .then((res) => {
          this.$message.success(res.msg);
          this.goBack();
        })
        .catch(() => {
          this.$message.error("提交信息失败");
        });
      // 隐藏对话框并跳转
      this.tipDialogVisible = false;
    },
    // 成功函数
    succeed() {
      let formData = new FormData();
      formData.append("resumeId", this.info.resumeId);
      // 如果是通过筛选
      if (this.status == true) {
        formData.append("resumeState", "1");
      } else {
        // 如果是面试通过
        formData.append("resumeState", "2");
      }
      // 发送put请求
      ModifyResume.updateResumeState(formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        }
      })
        .then((res) => {
          this.$message.success(res.msg);
          this.goBack();
        })
        .catch((err) => {
          this.$message.error("提交信息失败");
        });
      // 隐藏对话框并跳转
      this.tipDialogVisible = false;
    },
    // 双击评语框变为编辑状态
    modify() {
      // 双击则将span转化为textarea
      document.querySelector(
        ".comment"
      ).innerHTML = `<textarea
      style="width: 100%;border-radius: 5px;outline: none;height: 80px;line-height: 20px;"
      >${this.info.comment}</textarea>`;
    },
  },
  created() {
    // 如果接受到数据，保存到sessionStorage
    if (this.resumeInfo.name) {
      // 问题1: 目前还不确定 created确确实实能够获取得到数据
      sessionStorage.setItem("resumeInfo", JSON.stringify(this.resumeInfo));
      // 问题2: 是否需要浅拷贝还是深拷贝
      this.info = this.resumeInfo
    } else {
      // 如果没接收到数据，从sessionStorage获取数据进行渲染
      this.info = JSON.parse(sessionStorage.getItem("resumeInfo"));
      this.workExperience = eval("(" + this.info.workExperience + ")");
    }
  },
  // 不知道watch是否有用,待定选项
  watch: {
    resumeInfo(val) {
      if (!this.info.name) {
        this.info = val;
      }
    },
  },
};
</script>

<style lang='less' scoped>
@import "../../assets/css/children/ExamineResume.less";
</style>