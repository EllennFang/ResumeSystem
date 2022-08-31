<template>
  <div id="" class="short-message">
    <div class="header">
      <div class="m-manager-back">
        <router-link :to="{ name: 'Manager' }">后台</router-link>
      </div>
      <div>
        <el-dropdown @command="handleChooseSMSState" trigger="click">
          <span class="el-dropdown-link">
            {{ resumeStateInfo }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              v-for="(item, index) in resumeStateGroup"
              :key="index"
              :command="index"
              >{{ item }}</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div class="choose-SMS-template">
        <!-- <el-dropdown @command="chooseSMSState" trigger="click">
          <span class="el-dropdown-link">
            短信模板<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              v-for="(item, index) in resumeStateGroup"
              :key="index"
              :command="index"
              >{{ item }}</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown> -->
      </div>
    </div>
    <div class="bannber">
      <div class="cascader-1">
        <el-select
          v-model="SMSTemplateType"
          placeholder="类型"
          @change="handleSelectTypeChange"
        >
          <el-option
            v-for="item in SMSTemplateTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
      <div class="cascader-2">
        <el-select
          v-model="SMSTemplateChoice"
          placeholder="请选择模板"
          :disabled="SMSTemplateType == ''"
        >
          <el-option
            v-for="item in SMSTemplateChoiceOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="container">
      <div class="student-list">
        <div class="student-item" v-for="item in studentList" :key="item.id">
          <div class="left">
            <div class="basic-info">
              <div class="name">{{ item.name }}</div>
              <span><i>学号：</i>{{ item.studentId }}</span>
              <span><i>电话：</i>{{ item.phone }}</span>
            </div>
            <div class="intension-group">
              <i>意向组别：</i>
              <span
                class="intentsion-group-item"
                v-for="(item2, index2) in item.intensionGroup"
                :key="index2"
              >
                <el-tag type="success" disable-transitions>{{ item2 }}</el-tag>
              </span>
              <!-- <el-tag type="success" disable-transitions>{{ item.intensionGroup }}</el-tag> -->
            </div>
            <div class="SMS-state">
              <i>短信发送状态：</i>
              <span
                v-if="item.smsstate == '0' || item.smsstate == '2'"
                class="state-text-color-warn"
                >{{ SMSStateInfoGroup[item.smsstate] }}</span
              >
              <el-popconfirm
                cancel-button-text="不用了"
                title="是否确认将短信发送状态改为发送失败"
                v-if="item.smsstate == '1' || item.smsstate == '3'"
                @confirm="() => handleChangeSMSStateClick(item)"
              >
                <span
                  class="state-text-color-success"
                  slot="reference"
                  >{{ SMSStateInfoGroup[item.smsstate] }}</span
                >
              </el-popconfirm>
            </div>
          </div>
          <p class="right" @click="() => sendSMS(item)">发送</p>
        </div>
      </div>
    </div>
    <div class="footer">
      <!-- 分页区域 -->
      <div class="pagination">
        <el-pagination
          small
          hide-on-single-page
          :page-size="10"
          :page-count="paginationInfo.pageCount"
          :current-page="paginationInfo.currentPage"
          @current-change="handlePagesChange"
        >
        </el-pagination>
      </div>
      <div class="no-more" v-if="paginationInfo.pageCount == 1">
        - 没有更多了-
      </div>
    </div>
    <!-- <a :href="'sms:18206668043?body=' + msg">发送电信</a> -->
  </div>
</template>

<script>
import { api_SMSTemplate, queryResumeMethods } from "../network/admin";

export default {
  data() {
    this.intensionGroupValueArr = [
      "软件组",
      "硬件组",
      "设计组",
      "算法组",
      "创想联盟组",
    ];
    this.SMSStateInfoGroup = [
      "未发送",
      "筛选结果已发送",
      "发送失败",
      "面试结果已发送",
    ];
    this.resumeStateGroup = [
      "未筛选",
      "通过筛选",
      "通过面试",
      "未通过面试",
      "未通过筛选",
    ];
    this.SMSTemplateTypeOptions = [
      {
        value: "1",
        label: "通过",
      },
      {
        value: "2",
        label: "未通过",
      },
    ];
    return {
      resumeState: 0,
      page: 1,
      studentList: [],
      paginationInfo: {
        pageCount: 0,
        currentPage: 1,
      },
      SMSTemplateType: "",
      SMSTemplateChoice: "",
      SMSTemplateChoiceOptions: [
        {
          value: "0",
          label: "title1",
        },
        {
          value: "1",
          label: "title2",
        },
      ],
      SMSTemplates: {},
      SMSTemplateOptionsInfo: {},
    };
  },
  computed: {
    resumeStateInfo() {
      return this.resumeStateGroup[this.resumeState];
    },
  },
  methods: {
    // 获取简历
    async getResume() {
      let params = {
        resumeState: this.resumeState,
        page: this.page,
      };
      try {
        let res = await queryResumeMethods.queryByResumeState(params);
        if (!res.state) throw new Error("简历获取失败");
        res.data = res.data.map((item1) => {
          // 如果仅修改数组或对象，引用数据类型，可以不 return ，也不用接收新数组
          item1.intensionGroup = item1.intensionGroup.split(",");
          // 将 intensionGroup 数组中的数字替换为 软件组、硬件组、设计组、算法组、创想联盟组
          item1.intensionGroup = item1.intensionGroup.map((item2) => {
            item2 = this.intensionGroupValueArr[item2 - 1];
            return item2;
          });
          return item1;
        });
        this.paginationInfo.pageCount = res.pages;
        this.studentList = res.data;
        // this.studentList = this.studentList.concat(res.data);
      } catch (err) {
        this.$message.error("简历获取失败");
      }
    },
    // 获取短信模板
    async getSMSTemplate() {
      try {
        let res = await api_SMSTemplate.getTemplates();
        if (!res.state) throw new Error(res.msg);
        res = res.data;
        this.SMSTemplates = res;
        this.SMSTemplateOptionsInfo.interviewSuccess = res.interviewSuccess.map(
          (item) => {
            return {
              value: item.id,
              label: item.title,
            };
          }
        );
        this.SMSTemplateOptionsInfo.interviewFailed = res.interviewFailed.map(
          (item) => {
            return {
              value: item.id,
              label: item.title,
            };
          }
        );
      } catch (error) {
        this.$message.error("短信模板获取失败");
      }
    },
    // 选择短信发送状态
    handleChooseSMSState(command) {
      this.resumeState = command;
      this.page = 1;
      this.getResume();
    },
    // 切换页数
    handlePagesChange(page) {
      this.page = page;
      this.getResume();
    },
    // 发送短信
    async sendSMS(resumeInfo) {
      if (this.SMSTemplateChoice == "") {
        // 未选择短信模板
        this.$message.warning("请选择短信模板");
        return;
      }
      try {
        // 如果未选择模板，阻止后续操作并提示
        // ....
        let formData = new FormData();
        formData.append("resumeId",resumeInfo.resumeId);
        formData.append("SMSState",1);
        let res = await api_SMSTemplate.updateSMSState(formData,{
          headers: { "Content-Type": "multipart/form-data" },
        });
        if (!res.state) throw new Error(res.msg);
        for (let resume of this.studentList) {
          if (resume.id == resumeInfo.id) {
            resume.smsstate = "1";
            break;
          }
        }
        let msg = "";
        let templates = [];
        if (this.SMSTemplateType == "1") {
          templates = this.SMSTemplates.interviewSuccess;
        } else if (this.SMSTemplateType == "2") {
          templates = this.SMSTemplates.interviewFailed;
        }
        for (let item of templates) {
          if (item.id === this.SMSTemplateChoice) {
            msg = item.content
              .replace(/<name>/g, resumeInfo.name)
              .replace(/<software>/g, "软件组")
              .replace(/<hardware>/g, "硬件组")
              .replace(/<design>/g, "设计组")
              .replace(/<algorithm>/g, "算法组")
              .replace(/<union>/g, "创想联盟组");
            break;
          }
        }
        const element = document.createElement("a");
        element.href = `sms:${resumeInfo.phone}?body=${msg}`;
        element.click();
      } catch (err) {
        this.$message.error("短信发送失败");
      }
    },
    // 选择短信模板类型
    handleSelectTypeChange() {
      this.SMSTemplateChoice = "";
      if (this.SMSTemplateType === "1") {
        // 选择通过模板
        this.SMSTemplateChoiceOptions =
          this.SMSTemplateOptionsInfo.interviewSuccess;
      } else if (this.SMSTemplateType === "2") {
        // 选择未通过模板
        this.SMSTemplateChoiceOptions =
          this.SMSTemplateOptionsInfo.interviewFailed;
      }
    },
    // 修改短信发送状态
    async handleChangeSMSStateClick(resumeInfo) {
      try {
        let formData = new FormData();
        formData.append("resumeId",resumeInfo.resumeId);
        formData.append("SMSState",2);
        // let params = {
        //   resumeId: resumeInfo.resumeId,
        //   SMSState: 2,
        // };
        let res = await api_SMSTemplate.updateSMSState(formData,{
          headers: { "Content-Type": "multipart/form-data" },
        });
        if (!res.state) throw new Error(res.msg);
        for (let resume of this.studentList) {
          if (resume.id == resumeInfo.id) {
            resume.smsstate = "2";
            break;
          }
        }
        this.$message.success("修改成功");
      } catch (err) {
        this.$message.error("修改失败");
      }
      // this.$axios({
      //   method: "put",
      //   url: "http://8.129.163.145:10012/admin/updateSMSState",

      // })
    },
  },
  created() {
    this.getResume();
    this.getSMSTemplate();
    // const throttleFn = this.$PublicFn.throttle(() => {
    //   // let scrollBottom = document.documentElement.scrollBottom || window.pageYOffset || document.body.scrollTop;
    //   if (document.documentElement.scrollBottom != undefined) {
    //     console.log(`第一种${document.documentElement.scrollBottom}`);
    //   } else if (window.pageYOffset != undefined) {
    //     // console.log(document.body.clientHeight - window.screen.availHeight);
    //     // console.log(`第二种${window.pageYOffset}`);
    //     if (window.pageYOffset > document.body.clientHeight - window.screen.availHeight - 10) {
    //       console.log(11111);
    //     }
    //   } else {
    //     console.log(`第三种${document.body.scrollTop}`);
    //   }
    // }, 300);
    // window.addEventListener("scroll", throttleFn);
    // this.$once("hook:beforeDestroy", () => {
    //   window.removeEventListener("scroll", throttleFn);
    // });
  },
};
</script>

<style lang='less' scoped>
@import "../assets/css/global.less";
@import "../assets/css/ShortMessage.less";
</style>