<template>
  <div id="">
    <el-card>
      <div class="SMS-tips">
        <div class="text-info">短信模板</div>
        <div class="text-tip">
          请输入短信内容，用于短信发送，<br />
          系统将自动解析短信模板内容并替换其中的姓名等信息。<br />
          <strong>注：</strong>姓名使用<strong>"&lt;name&gt;"</strong>代替<br />
          <p>软件组：<strong>"&lt;software&gt;"</strong></p>
          <p>硬件组：<strong>"&lt;hardware&gt;"</strong></p>
          <p>设计组：<strong>"&lt;design&gt;"</strong></p>
          <p>算法组：<strong>"&lt;algorithm&gt;"</strong></p>
          <p>创想联盟组：<strong>"&lt;union&gt;"</strong></p>
        </div>
      </div>
      <el-form
        class="form-tem"
        label-position="right"
        label-width="120px"
        :model="SMSTemplateData"
        :rules="SMSTemplateFormRules"
        ref="SMSTemplateFormRef"
      >
        <el-form-item label="模板类型：" prop="type">
          <el-select
            class="tem-type-select"
            v-model.trim="SMSTemplateData.type"
            placeholder="请选择类型"
            size="medium"
            style="width: 229px"
          >
            <el-option
              v-for="item in SMSTemplateOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="模板标题：" prop="title">
          <el-input
            class="tem-title-input"
            placeholder="请输入短信模板标题"
            v-model="SMSTemplateData.title"
            maxlength="100"
          ></el-input>
        </el-form-item>
        <el-form-item label="模板内容：" prop="content">
          <el-input
            class="tem-content-input"
            type="textarea"
            placeholder="请输入短信模板内容"
            :autosize="{ minRows: 2, maxRows: 4 }"
            v-model="SMSTemplateData.content"
            maxlength="1000"
            show-word-limit
          >
          </el-input>
        </el-form-item>
        <el-button class="SMS-template-save" @click="saveSMSTemplate"
          >保存</el-button
        >
      </el-form>
      <div class="SMS-template">
        <div class="SMS-title">简历审核通过的短信模板</div>
        <el-collapse v-model="activeNames">
          <el-collapse-item
            v-for="(item, index) in SMSTemplateList.interviewSuccess"
            :key="item.id"
            :title="item.title"
            :name="'1-' + index"
          >
            <div>{{ item.content }}</div>
            <div class="button-delete">
              <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="不用了"
                icon="el-icon-info"
                icon-color="red"
                title="确定删除吗？"
                @confirm="handleDelete('0', item.id)"
              >
                <el-button size="mini" type="danger" slot="reference"
                  >删除</el-button
                >
              </el-popconfirm>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
      <div class="SMS-template">
        <div class="SMS-title">简历审核未通过的短信模板</div>
        <el-collapse v-model="activeNames">
          <el-collapse-item
            v-for="(item, index) in SMSTemplateList.interviewFailed"
            :key="item.id"
            :title="item.title"
            :name="'2-' + index"
          >
            <div>{{ item.content }}</div>
            <div class="button-delete">
              <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="不用了"
                icon="el-icon-info"
                icon-color="red"
                title="确定删除吗？"
                @confirm="handleDelete('0', item.id)"
              >
                <el-button size="mini" type="danger" slot="reference"
                  >删除</el-button
                >
              </el-popconfirm>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </el-card>
  </div>
</template>

<script>
import { api_SMSTemplate } from "../../network/admin";

export default {
  name: "",
  data() {
    return {
      SMSValue: "",
      activeNames: [],
      SMSTemplateOptions: [
        {
          value: "1",
          label: "审核通过",
        },
        {
          value: "2",
          label: "审核未通过",
        },
      ],
      SMSTemplateData: {
        type: "",
        title: "",
        content: "",
      },
      SMSTemplateList: {
        interviewSuccess: [],
        interviewFailed: [],
      },
      //表单的验证规则对象
      SMSTemplateFormRules: {
        type: [{ required: true, message: "请选择模板类型", trigger: "blur" }],
        title: [{ required: true, message: "请输入模板标题", trigger: "blur" }],
        content: [
          { required: true, message: "请输入模板内容", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    // 保存短信模板
    saveSMSTemplate() {
      this.$refs["SMSTemplateFormRef"].validate(async (valid) => {
        if (!valid) {
          this.$message.success("短信模板保存失败");
          return false;
        }
        // let formData = new FormData();
        let { SMSTemplateData } = this;
        let newObj = {
          type: SMSTemplateData.type,
          title: SMSTemplateData.title,
          content: SMSTemplateData.content,
        };
        try {
          let res = await api_SMSTemplate.saveTemplate(newObj);
          if (!res.state) throw new Error(res.msg);
          this.$message.success(res.msg);
          this.SMSTemplateData.type = "";
          this.SMSTemplateData.title = "";
          this.SMSTemplateData.content = "";
          this.getSMSTemplate();
        } catch (err) {
          console.log(err);
          this.$message.error("短信模板保存失败");
        }
        // if (SMSTemplateData.type == "0") {
        //   this.SMSTemplateList.interviewSuccess.push(newObj);
        // } else {
        //   this.SMSTemplateList.failed.push(newObj);
        // }
        // this.$message.success("短信模板保存成功！");
        // this.SMSTemplateList.interviewSuccess.map((item) => {
        //   item.content = item.content.replace(/<name>/g, "xxx");
        //   return item;
        // });
      });
    },
    // 删除短信模板
    async handleDelete(type, id) {
      const data = {
        id,
      };
      try {
        let res = await api_SMSTemplate.deleteTemplate(data);
        console.log(res);
        this.$message.success("删除成功");
      } catch (err) {
        this.$message.error("删除失败！");
      }
      this.getSMSTemplate();
    },
    // 获取短信模板
    async getSMSTemplate() {
      try {
        let res = await api_SMSTemplate.getTemplates();
        if (!res.state) throw Error("短信模板获取失败");
        res = res.data;
        this.SMSTemplateList.interviewSuccess = res.interviewSuccess;
        this.SMSTemplateList.interviewFailed = res.interviewFailed;
      } catch (err) {
        this.$message.error("短信模板获取失败");
        console.log(err);
      }
    },
  },
  mounted() {
    this.getSMSTemplate();
  },
};
</script>

<style lang='less' scoped>
@import "../../assets/css/global.less";
@import "../../assets/css/children/SMSTemplate.less";
</style>