<template>
  <div class="body-bg">
    <div class="resume-container">
      <div class="resume-box">
        <!-- 头像区域 -->
        <div class="avatar-box">
          <img
            src="../assets/images/cloudhouselogo.jpg"
            @click="routerSearchResume"
            alt="logo"
          />
        </div>
        <!-- 标题区域 -->
        <div class="title-box">
          <h1>大云屋•众创空间<br />报名简历</h1>
        </div>
        <!-- 表单区域 -->
        <!-- <button @click="testBtn">测试用按钮，一键填满信息</button> -->
        <el-steps
          :active="2"
          class="steps"
          align-center
          v-if="resumeState != '-1'"
        >
          <el-step
            v-for="(item, index) in stepsStatus"
            :key="index"
            :title="item.title"
            :description="item.description"
            :status="item.status"
          ></el-step>
        </el-steps>
        <el-form
          label-width="0"
          :model="resumeData"
          :rules="resumeFormRules"
          ref="resumeDataRef"
          class="resume-form"
        >
          <!-- 基础信息 -->
          <h2>基础信息</h2>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span><i class="require">*</i>姓名：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="name">
                <el-input
                  size="medium"
                  placeholder="请输入姓名"
                  maxlength="20"
                  v-model.trim="resumeData.name"
                ></el-input>
              </el-form-item>
              <!-- <div class="form-item-err">请输入姓名</div> -->
            </el-col>
            <el-col :span="3" :offset="3">
              <div class="text-content">
                <span><i class="require">*</i>联系电话：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="phone">
                <el-input
                  size="medium"
                  placeholder="请输入联系电话"
                  maxlength="11"
                  v-model.trim="resumeData.phone"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span><i class="require">*</i>性别：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="sex">
                <el-select
                  v-model.trim="resumeData.sex"
                  placeholder="请选择性别"
                  size="medium"
                  style="width: 229px"
                >
                  <el-option
                    v-for="item in options.sexOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="3" :offset="3">
              <div class="text-content">
                <span><i class="require">*</i>年级：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="grade">
                <el-select
                  v-model.trim="resumeData.grade"
                  placeholder="请选择年级"
                  size="medium"
                  style="width: 229px"
                >
                  <el-option
                    v-for="item in options.gradeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span><i class="require">*</i>学院全称：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="department">
                <el-input
                  size="medium"
                  placeholder="请输入学院全称"
                  maxlength="100"
                  v-model.trim="resumeData.department"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3" :offset="3">
              <div class="text-content">
                <span><i class="require">*</i>专业：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="major">
                <el-input
                  size="medium"
                  placeholder="请输入专业名称"
                  maxlength="100"
                  v-model.trim="resumeData.major"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span><i class="require">*</i>QQ：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="QQ">
                <el-input
                  size="medium"
                  placeholder="请输入QQ号"
                  maxlength="15"
                  v-model.trim="resumeData.QQ"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3" :offset="3">
              <div class="text-content">
                <span><i class="require">*</i>宿舍号：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="address">
                <el-input
                  size="medium"
                  placeholder="请输入宿舍号"
                  maxlength="100"
                  v-model.trim="resumeData.address"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span><i class="require">*</i>学号：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="studentId">
                <el-input
                  size="medium"
                  placeholder="请输入学号"
                  maxlength="10"
                  v-model.trim="resumeData.studentId"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3" :offset="3">
              <div class="text-content">
                <span><i class="require">*</i>邮箱地址：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="mailaddress">
                <el-input
                  size="medium"
                  placeholder="请输入邮箱地址"
                  maxlength="100"
                  v-model.trim="resumeData.mailaddress"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-divider></el-divider>
          <!-- 报名信息 -->
          <h2>报名信息</h2>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span><i class="require">*</i>意向组别：</span>
              </div>
            </el-col>
            <el-col :span="21">
              <el-form-item prop="intensionGroup">
                <el-checkbox-group
                  v-model.trim="resumeData.intensionGroup"
                  size="medium"
                >
                  <el-checkbox-button :label="options.group[0]"
                    >软件组</el-checkbox-button
                  >
                  <el-checkbox-button :label="options.group[1]"
                    >硬件组</el-checkbox-button
                  >
                  <el-checkbox-button :label="options.group[2]"
                    >设计组</el-checkbox-button
                  >
                  <el-checkbox-button :label="options.group[3]"
                    >算法组</el-checkbox-button
                  >
                  <el-checkbox-button :label="options.group[4]"
                    >创想联盟组</el-checkbox-button
                  >
                </el-checkbox-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span
                  ><i class="require"><i class="require">*</i></i
                  >接受调剂：</span
                >
              </div>
            </el-col>
            <el-col :span="5">
              <el-form-item prop="adjustment">
                <el-radio-group v-model.trim="resumeData.adjustment">
                  <el-radio-button label="是"></el-radio-button>
                  <el-radio-button label="否"></el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span>报名原因：</span>
              </div>
            </el-col>
            <el-col :span="20">
              <el-form-item prop="joinReason">
                <el-input
                  type="textarea"
                  placeholder="请填写报名原因"
                  maxlength="2000"
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.joinReason"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span>自身优势：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="advantage">
                <el-input
                  type="textarea"
                  placeholder="请填写自身优势"
                  maxlength="2000"
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.advantage"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="3" :offset="3">
              <div class="text-content">
                <span>自身劣势：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="disadvantage">
                <el-input
                  type="textarea"
                  placeholder="请填写自身劣势"
                  maxlength="2000"
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.disadvantage"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span>未来规划：</span>
              </div>
            </el-col>
            <el-col :span="20">
              <el-form-item prop="futurePlanning">
                <el-input
                  type="textarea"
                  placeholder="请填写未来规划"
                  maxlength="2000"
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.futurePlanning"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- <h3>工作经历</h3> -->
          <el-row>
            <el-col :span="23">
              <el-divider content-position="left"><b>工作经历</b></el-divider>
            </el-col>
          </el-row>
          <div v-if="isShowWork">
            <el-row :gutter="6">
              <el-col :span="3">
                <div class="text-content">
                  <span><i class="require">*</i>社团名称：</span>
                </div>
              </el-col>
              <el-col :span="20">
                <el-input
                  size="medium"
                  placeholder="请输入社团名称"
                  maxlength="100"
                  v-model.trim="temWorkExperience.associationName"
                  @blur="blurWorkEmpty('associationName', $event)"
                ></el-input>
                <!-- 社团名称输入验证是blur事件和watch一起实现的 -->
                <div
                  class="form-item-err"
                  v-if="temWorkExperience.isShowAssociationName"
                >
                  请输入社团名称
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="6">
              <el-col :span="3">
                <div class="text-content">
                  <span><i class="require">*</i>起止时间：</span>
                </div>
              </el-col>
              <el-col :span="7">
                <el-date-picker
                  v-model.trim="temWorkExperience.workDate"
                  type="monthrange"
                  range-separator="至"
                  start-placeholder="开始月份"
                  end-placeholder="结束月份"
                  size="medium"
                  value-format="yyyy-MM-dd"
                  @blur="blurworkDateEmpty('workDate', $event)"
                >
                </el-date-picker>
                <div
                  class="form-item-err"
                  v-if="temWorkExperience.isShowWorkDate"
                >
                  请选择起止时间
                </div>
              </el-col>
              <el-col :span="3" :offset="3">
                <div class="text-content">
                  <span><i class="require">*</i>担任职务：</span>
                </div>
              </el-col>
              <el-col :span="7">
                <el-input
                  size="medium"
                  placeholder="请输入所担任的职务"
                  maxlength="100"
                  v-model.trim="temWorkExperience.postName"
                  @blur="blurWorkEmpty('postName', $event)"
                ></el-input>
                <!-- 担任职务输入验证是blur事件和watch一起实现的 -->
                <div
                  class="form-item-err"
                  v-if="temWorkExperience.isShowPostName"
                >
                  请输入担任职务
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="6">
              <el-col :span="3">
                <div class="text-content">
                  <span>工作内容：</span>
                </div>
              </el-col>
              <el-col :span="20">
                <el-input
                  type="textarea"
                  placeholder="请填写工作内容"
                  maxlength="2000"
                  show-word-limit
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="temWorkExperience.job"
                >
                </el-input>
              </el-col>
            </el-row>
            <el-row :gutter="6">
              <el-col :offset="3" :span="6">
                <el-button
                  type="primary"
                  size="medium"
                  round
                  class="save-resume-btn"
                  @click="saveWorkExperience"
                  >保存</el-button
                >
                <el-button
                  size="medium"
                  round
                  class="cancel-resume-btn"
                  @click="clearWorkExperience"
                  >取消</el-button
                >
              </el-col>
              <el-col :span="4" :offset="10" style="text-align: right">
                <el-button
                  type="danger"
                  size="medium"
                  round
                  class="del-resume-btn"
                  @click="delWorkExperience"
                  v-if="editState == 2"
                  >删除</el-button
                >
              </el-col>
            </el-row>
            <br v-if="resumeData.workExperience.length != 0" />
          </div>
          <el-form-item prop="workExperience">
            <div
              v-for="(work, index) in resumeData.workExperience"
              :key="index"
            >
              <div v-if="index != editIndex">
                <el-row :gutter="6">
                  <el-col :span="21">
                    {{ work.associationName }}
                    <el-divider direction="vertical"></el-divider>
                    {{ work.workDate[0] }}~{{ work.workDate[1] }}
                    <el-divider direction="vertical"></el-divider>
                    {{ work.postName }}
                  </el-col>
                  <el-col :span="2">
                    <div
                      v-if="isShowEdit"
                      class="text-content edit-resume-btn"
                      @click="editWorkExperience(index)"
                    >
                      编辑
                    </div>
                  </el-col>
                </el-row>
                <el-row> 工作内容：{{ work.job }} </el-row>
                <br v-if="index != resumeData.workExperience.length - 1" />
              </div>
            </div>
          </el-form-item>
          <div
            class="add-resume-btn"
            v-show="!isShowWork"
            @click="addWorkExperience"
          >
            添加工作经历
          </div>
          <!-- <el-row class="add-resume-btn">
          <el-button @click="add">添加工作经历</el-button>
        </el-row> -->
          <el-divider></el-divider>
          <!-- 其他信息 -->
          <h2>其他信息</h2>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span>可支配时间：</span>
              </div>
            </el-col>
            <el-col :span="20">
              <el-form-item prop="disposableTime">
                <el-input
                  type="textarea"
                  placeholder="请填写可支配时间"
                  maxlength="2000"
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.disposableTime"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span>在校奖励：</span>
              </div>
            </el-col>
            <el-col :span="20">
              <el-form-item prop="schoolAward">
                <el-input
                  type="textarea"
                  placeholder="请填写在校获得的奖励"
                  maxlength="2000"
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.schoolAward"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content">
                <span>自我介绍：</span>
              </div>
            </el-col>
            <el-col :span="20">
              <el-form-item prop="selfIntroduction">
                <el-input
                  type="textarea"
                  placeholder="请进行自我介绍，不超过两千字"
                  maxlength="2000"
                  show-word-limit
                  :autosize="{ minRows: 2, maxRows: 5 }"
                  v-model="resumeData.selfIntroduction"
                >
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="6">
            <el-col :span="3">
              <div class="text-content code-key-text">
                <span><i class="require">*</i>验证码：</span>
              </div>
            </el-col>
            <el-col :span="7">
              <el-form-item prop="codeKeyInput">
                <el-input
                  placeholder="请输入验证码"
                  maxlength="4"
                  v-model.trim="resumeData.codeKeyInput"
                >
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="7">
              <div class="code-img-btn">
                <img :src="codeImgString" @click="changeCodeImg" alt="验证码" />
                <span>
                  <a href="javascript: void(0);" @click="changeCodeImg"
                    >看不清，换一张
                  </a>
                </span>
              </div>
            </el-col>
          </el-row>
          <el-row type="flex" justify="center" class="row-submit">
            <el-button
              type="primary"
              class="submit-resume-btn"
              @click="submitResumeForm('resumeDataRef')"
              v-if="this.resumeState == -1 || this.resumeState == 0"
              >提交简历</el-button
            >
          </el-row>
          <el-dialog
            title="简历提交成功！"
            :visible.sync="tipDialogVisible"
            :append-to-body="true"
            width="30%"
            class="el-dialog-tip"
          >
            <span>简历凭证码为：</span>
            <h3 ref="dialogResumeIdRef"></h3>
            <p>请保存好简历凭证码，后续可使用凭证码修改或查询简历状态</p>
            <span slot="footer" class="dialog-footer">
              <el-button @click="tipDialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="tipDialogVisible = false"
                >确 定</el-button
              >
            </span>
          </el-dialog>
          <br />
          <div class="right-box">
            <div class="right-box-children right-box-search-resume">
              <el-row :gutter="6">
                <!-- <el-col :span="16">
                  <el-input
                    placeholder="请输入学号"
                    maxlength="10"
                    v-model.trim="searchStudentId"
                  ></el-input> -->
                <!-- 查看简历的输入验证是blur事件和watch一起实现的 -->
                <!-- <div
                    class="form-item-err"
                    v-if="rightBox.isShowSearchResumeWarnOne"
                  >
                    请输入学号
                  </div>
                </el-col>
                <el-col :span="8">
                  <el-button
                    type="primary"
                    @click="searchResume('checkByStudentId')"
                    style="width: 80px; padding-left: 12px"
                    >查看简历</el-button
                  >
                </el-col> -->
                <el-button type="primary" @click="routerSearchResume"
                  >查询简历</el-button
                >
              </el-row>
              <!-- <el-row :gutter="6">
                <el-col :span="16">
                  <el-input
                    placeholder="请输入凭证码"
                    v-model.trim="searchResumeId"
                  ></el-input> -->
              <!-- 查看简历的输入验证是blur事件和watch一起实现的 -->
              <!-- <div
                    class="form-item-err"
                    maxlength="100"
                    v-if="rightBox.isShowSearchResumeWarnTwo"
                  >
                    请输入凭证码
                  </div>
                </el-col>
                <el-col :span="8">
                  <el-button
                    type="primary"
                    @click="searchResume('checkByResumeId')"
                    style="width: 80px; padding-left: 12px"
                    >查看简历</el-button
                  >
                </el-col>
              </el-row> -->
            </div>
            <div class="right-box-children right-box-file">
              <div>
                <input
                  type="file"
                  ref="projectFileRef"
                  style="display: none"
                  @change="handleProjectFile()"
                />
                <el-button
                  size="small"
                  type="primary"
                  ref="submitFileBtnRef"
                  @click="upLoadProjectFile()"
                  >上传文件</el-button
                >
                <el-dialog
                  title="提示"
                  :visible.sync="dialogVisible"
                  :append-to-body="true"
                  width="30%"
                >
                  <span v-if="resumeData.projectFile.size != 0"
                    >警告，若继续上传将替换原有文件</span
                  >
                  <span v-else>您确定要上传文件吗？</span>
                  <span slot="footer" class="dialog-footer">
                    <el-button @click="dialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="dialogContinue()"
                      >确 定</el-button
                    >
                  </span>
                </el-dialog>
                <div class="upload-file-name" ref="upLoadTipRef"></div>
                <div slot="tip" class="el-upload__tip">
                  只能上传jpg|png|jpeg|doc|docx|pdf|zip|rar|7z文件，最多1个文件，总大小不能超过20M
                </div>
              </div>
            </div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { resume_api } from "../network/student";
export default {
  data() {
    return {
      // 简历状态
      resumeState: "-1",
      stepsStatus: [
        {
          status: "success",
          title: "提交简历",
          description: "提交成功",
        },
        {
          status: "finish",
          title: "筛选结果",
          description: "筛选中",
        },
        {
          status: "wait",
          title: "面试结果",
          description: "",
        },
      ],
      //Resume表单数据
      resumeData: {
        name: "",
        phone: "",
        sex: "",
        grade: "",
        department: "",
        major: "",
        QQ: "",
        mailaddress: "",
        address: "",
        studentId: "", //学号
        intensionGroup: [],
        adjustment: "",
        joinReason: "",
        advantage: "",
        disadvantage: "",
        futurePlanning: "",
        workExperience: [],
        disposableTime: "",
        schoolAward: "",
        selfIntroduction: "",
        codeKeyInput: "", //验证码
        codeKey: "", //验证验证码时的参数
        resumeId: null, //凭证码
        projectFile: new Blob(), //作品文件
      },
      // 临时储存工作经历信息
      temWorkExperience: {
        associationName: "",
        workDate: "",
        postName: "",
        job: "",
        isShowAssociationName: false,
        isShowWorkDate: false,
        isShowPostName: false,
      },
      //表单的验证规则对象
      resumeFormRules: {
        //验证姓名是否合法
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
        //验证电话是否合法
        phone: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
          {
            pattern: /^[1][0-9]{10}$/,
            message: "请输入正确的电话号码",
            trigger: "blur",
          },
          {
            pattern: /^[0-9]*$/,
            message: "请输入正确的电话号码",
            trigger: "change",
          },
        ],
        //验证性别是否合法
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        //验证年级是否合法
        grade: [{ required: true, message: "请选择年级", trigger: "change" }],
        //验证学院是否合法
        department: [
          { required: true, message: "请输入学院全称", trigger: "blur" },
          {
            pattern: /^[\u4E00-\u9FA5]*$/,
            message: "请输入正确的学院名称",
            trigger: "blur",
          },
          {
            pattern: /^[\u4E00-\u9FA5]*$/,
            message: "请输入正确的学院名称",
            trigger: "change",
          },
        ],
        //验证专业是否合法
        major: [
          { required: true, message: "请输入专业名称", trigger: "blur" },
          {
            pattern: /^[\u4E00-\u9FA5]*$/,
            message: "请输入正确的专业名称",
            trigger: "blur",
          },
          {
            pattern: /^[\u4E00-\u9FA5]*$/,
            message: "请输入正确的专业名称",
            trigger: "change",
          },
        ],
        //验证QQ是否合法
        QQ: [
          { required: true, message: "请输入QQ", trigger: "blur" },
          { pattern: /^\d*$/, message: "请输入正确的QQ", trigger: "blur" },
          { pattern: /^\d*$/, message: "请输入正确的QQ", trigger: "change" },
        ],
        //验证邮箱是否合法
        mailaddress: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          { required: true, message: "请输入邮箱地址", trigger: "change" },
          {
            pattern:
              /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
            message: "请输入正确的邮箱地址",
            trigger: "blur",
          },
        ],
        //验证学号是否合法
        studentId: [
          { required: true, message: "请输入学号", trigger: "blur" },
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
        //验证宿舍号是否合法
        address: [
          { required: true, message: "请输入宿舍号", trigger: "blur" },
          {
            pattern: /^([\u4E00-\u9FA5]|\d)*$/,
            message: "请输入宿舍号",
            trigger: "blur",
          },
          {
            pattern: /^([\u4E00-\u9FA5]|\d)*$/,
            message: "请输入宿舍号",
            trigger: "change",
          },
        ],
        //验证意向组别是否合法
        intensionGroup: [
          { required: true, message: "请选择意向组别", trigger: "change" },
        ],
        //验证接受调剂是否合法
        adjustment: [
          { required: true, message: "请选择是否接受调剂", trigger: "change" },
        ],
        //验证验证码是否合法
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
      options: {
        sexOptions: [
          {
            value: "男",
            label: "男",
          },
          {
            value: "女",
            label: "女",
          },
        ],
        gradeOptions: [
          {
            value: "大一",
            label: "大一",
          },
          {
            value: "大二",
            label: "大二",
          },
        ],
        group: ["1", "2", "3", "4", "5"], // 意向组别的值，对应显示的值["软件组", "硬件组", "设计组", "算法组", "创想联盟组"]
      },
      isShowWork: false,
      isShowEdit: true,
      editState: 1, //工作经历编辑状态码，1为新增，2为修改
      editIndex: -1, //工作经历数组编辑索引
      codeData: {},
      codeImgString: "",
      dialogVisible: false,
      tipDialogVisible: false,
      fileName: "",
      path: "",
    };
  },
  computed: {},
  watch: {
    resumeState: function (val) {
      if (val == "4") {
        // 未通过筛选
        this.stepsStatus[1].status = "error";
        this.stepsStatus[1].description = "未通过筛选";
      } else if (val == "1") {
        // 通过筛选
        this.stepsStatus[1].status = "success";
        this.stepsStatus[1].description = "通过筛选";
        this.stepsStatus[2].status = "finish";
        this.stepsStatus[2].description = "面试中";
      } else if (val == "3") {
        // 未通过面试
        this.stepsStatus[1].status = "success";
        this.stepsStatus[1].description = "通过筛选";
        this.stepsStatus[2].status = "error";
        this.stepsStatus[2].description = "未通过面试";
      } else if (val == "2") {
        // 通过面试
        this.stepsStatus[1].status = "success";
        this.stepsStatus[1].description = "通过筛选";
        this.stepsStatus[2].status = "success";
        this.stepsStatus[2].description = "通过面试";
      }
    },
    "temWorkExperience.associationName": function (val) {
      //判断社团名称输入变化时，社团名称是否不为空
      if (this.temWorkExperience.associationName.replace(/^ +$/g, "") != "") {
        this.temWorkExperience.isShowAssociationName = false;
      }
    },
    "temWorkExperience.postName": function (val) {
      //判断职务输入变化时，担任职务是否不为空
      if (this.temWorkExperience.postName.replace(/^ +$/g, "") != "") {
        this.temWorkExperience.isShowPostName = false;
      }
    },
  },
  methods: {
    addWorkExperience() {
      this.isShowWork = !this.isShowWork;
      this.isShowEdit = !this.isShowEdit;
      // 编辑状态码改为新增：1
      this.editState = 1;
    },
    saveWorkExperience() {
      let allWorkVerification = true; //用于判断是否所有工作经历验证通过
      if (this.temWorkExperience.associationName.replace(/^ +$/g, "") == "") {
        this.temWorkExperience.isShowAssociationName = true; //社团名称验证不通过，显示提示信息
        allWorkVerification = false; //验证不通过
      }
      if (this.temWorkExperience.workDate == "") {
        this.temWorkExperience.isShowWorkDate = true; //起止时间验证不通过，显示提示信息
        allWorkVerification = false; //验证不通过
      }
      if (this.temWorkExperience.postName.replace(/^ +$/g, "") == "") {
        this.temWorkExperience.isShowPostName = true; //担任职务验证不通过，显示提示信息
        allWorkVerification = false; //验证不通过
      }
      if (!allWorkVerification) {
        return false;
      }
      //切换按钮显示状态
      this.isShowWork = !this.isShowWork;
      this.isShowEdit = !this.isShowEdit;

      // 深拷贝用户新输入的工作经历
      let temWorkObj = {
        associationName: this.temWorkExperience.associationName,
        workDate: this.temWorkExperience.workDate,
        postName: this.temWorkExperience.postName,
        job: this.temWorkExperience.job,
      };

      if (this.editState == 1) {
        //editState==1为新增内容
        //将用户输入信息添加到工作经历
        this.resumeData.workExperience.push(temWorkObj);
      } else if (this.editState == 2) {
        //editState==2为修改内容
        //将用户修改后的信息替换原来的工作经历
        this.resumeData.workExperience.splice(this.editIndex, 1, temWorkObj);
      } else {
        alert("保存出错");
      }

      //将temWorkExperience对象所有属性值重置为""
      Object.keys(this.temWorkExperience).map(
        (key) => (this.temWorkExperience[key] = "")
      );
      this.editIndex = -1;
    },
    clearWorkExperience() {
      this.isShowWork = !this.isShowWork;
      this.isShowEdit = !this.isShowEdit;

      //将temWorkExperience对象所有属性值重置为""
      Object.keys(this.temWorkExperience).map(
        (key) => (this.temWorkExperience[key] = "")
      );
      this.editIndex = -1;
    },
    delWorkExperience() {
      this.isShowWork = !this.isShowWork;
      this.isShowEdit = !this.isShowEdit;

      //将temWorkExperience对象所有属性值重置为""
      Object.keys(this.temWorkExperience).map(
        (key) => (this.temWorkExperience[key] = "")
      );

      //删除当前编辑信息对应的工作经历
      this.resumeData.workExperience.splice(this.editIndex, 1);
      this.editIndex = -1;
    },
    editWorkExperience(index) {
      this.isShowWork = !this.isShowWork;
      this.isShowEdit = !this.isShowEdit;
      // 编辑状态码改为修改：2
      this.editState = 2;
      this.editIndex = index;
      //将工作经历信息输入到编辑区域
      this.temWorkExperience = {
        associationName: this.resumeData.workExperience[index].associationName,
        workDate: this.resumeData.workExperience[index].workDate,
        postName: this.resumeData.workExperience[index].postName,
        job: this.resumeData.workExperience[index].job,
        isShowAssociationName: false,
        isShowWorkDate: false,
        isShowPostName: false,
      };
    },
    blurWorkEmpty(elem, e) {
      //判断哪个标签触发了blur事件
      switch (elem) {
        case "associationName":
          if (e.target.value.replace(/^ +$/g, "") == "") {
            this.temWorkExperience.isShowAssociationName = true; //验证不通过，显示提示信息
          } else {
            this.temWorkExperience.isShowAssociationName = false; //验证通过，隐藏提示信息
          }
          break;
        case "postName":
          if (e.target.value.replace(/^ +$/g, "") == "") {
            this.temWorkExperience.isShowPostName = true; //验证不通过，显示提示信息
          } else {
            this.temWorkExperience.isShowPostName = false; //验证通过，隐藏提示信息
          }
          break;
        default:
          console.log("blurWorkEmpty error");
      }
    },
    blurworkDateEmpty() {
      if (this.temWorkExperience.workDate == "") {
        this.temWorkExperience.isShowWorkDate = true; //验证不通过，显示提示信息
      } else {
        this.temWorkExperience.isShowWorkDate = false; //验证通过，隐藏提示信息
      }
    },
    async changeCodeImg() {
      try {
        let res = await resume_api.getCode({
          codeKey: this.resumeData.codeKey,
        });
        this.codeData = res;
        if (!res.state) {
          this.$message.error(res.msg);
          return;
        }
        this.resumeData.codeKey = res.codeKey;
        this.codeImgString = res.imgString;
      } catch (err) {
        this.$message.error("change CodeImg error");
      }
    },
    submitResumeForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (!valid) {
          //提示登陆失败，输入完整信息
          return false;
        }
        // 进行数据处理，将其升序
        this.resumeData.intensionGroup.sort((a, b) => {
          return a - b;
        });
        //将数据添加到 formData
        let formData = new FormData();
        formData.append("name", this.resumeData.name);
        formData.append("phone", this.resumeData.phone);
        formData.append("sex", this.resumeData.sex);
        formData.append("grade", this.resumeData.grade);
        formData.append("department", this.resumeData.department);
        formData.append("major", this.resumeData.major);
        formData.append("QQ", this.resumeData.QQ);
        formData.append("mailaddress", this.resumeData.mailaddress);
        formData.append("studentId", this.resumeData.studentId);
        formData.append("address", this.resumeData.address);
        formData.append(
          "intensionGroup",
          this.resumeData.intensionGroup.join(",")
        );
        formData.append("adjustment", this.resumeData.adjustment);
        formData.append("joinReason", this.resumeData.joinReason);
        formData.append("advantage", this.resumeData.advantage);
        formData.append("disadvantage", this.resumeData.disadvantage);
        formData.append("futurePlanning", this.resumeData.futurePlanning);
        formData.append(
          "workExperience",
          JSON.stringify(this.resumeData.workExperience)
        );
        formData.append("disposableTime", this.resumeData.disposableTime);
        formData.append("schoolAward", this.resumeData.schoolAward);
        formData.append("selfIntroduction", this.resumeData.selfIntroduction);
        formData.append("codeKeyInput", this.resumeData.codeKeyInput);
        formData.append("codeKey", this.resumeData.codeKey);
        formData.append("resumeId", this.resumeData.resumeId);
        formData.append("projectfile", this.resumeData.projectFile);
        try {
          let res = await resume_api.submitResume(formData, {
            headers: { "Content-Type": "multipart/form-data" },
          });
          if (!res.state) {
            // 出错
            this.changeCodeImg();
            this.$message.error(res.msg);
            return;
          }
          if (this.resumeData.resumeId == null) {
            //第一次提交简历
            this.resumeData.resumeId = res.resumeId;
            this.tipDialogVisible = true; //显示凭证码弹窗
            this.$nextTick(() => {
              this.$refs.dialogResumeIdRef.innerText = res.resumeId;
            });
          } else {
            // 提示简历修改成功
            this.$message.success(res.msg);
            setTimeout(() => {
              this.tipDialogVisible = true; //显示凭证码弹窗
              this.$nextTick(() => {
                this.$refs.dialogResumeIdRef.innerText =
                  this.resumeData.resumeId;
              });
            }, 500);
          }
          this.resumeData.codeKeyInput = "";
          this.changeCodeImg(); //改变验证码
        } catch (err) {
          this.$message.error("resume sunbmit error");
        }
        // resume_api
        //   .submitResume(formData, {
        //     headers: { "Content-Type": "multipart/form-data" },
        //   })
        //   // return;
        //   // this.$axios({
        //   //   method: "post",
        //   //   url: "http://8.129.163.145:10012/stu/resume",
        //   //   data: formData,
        //   //   headers: {
        //   //     "Content-Type": "multipart/form-data",
        //   //   },
        //   // })
        //   .then((res) => {
        //     // let res = response.data;
        //     if (!res.state) {
        //       // 出错
        //       this.changeCodeImg();
        //       this.$message.error(res.msg);
        //       return;
        //     }
        //     if (this.resumeData.resumeId == null) {
        //       //第一次提交简历
        //       this.resumeData.resumeId = res.resumeId;
        //       this.tipDialogVisible = true; //显示凭证码弹窗
        //       this.$nextTick(() => {
        //         this.$refs.dialogResumeIdRef.innerText = res.resumeId;
        //       });
        //     } else {
        //       // 提示简历修改成功
        //       this.$message.success(res.msg);
        //     }
        //     this.resumeData.codeKeyInput = "";
        //     this.changeCodeImg(); //改变验证码
        //   })
        //   .catch((err) => {
        //     console.log("resume sunbmit error");
        //   });
        //简历提交成功，展示凭证码

        //重置表单信息
        // this.$refs[formName].resetFields();
      });
    },
    upLoadProjectFile() {
      //判断是否已经上传文件
      if (this.resumeData.projectFile != null) {
        //已上传文件，弹出提示框，若继续上传将替换原有文件
        this.dialogVisible = true;
        return false;
      }
      if (this.$refs.projectFileRef) {
        this.$refs.projectFileRef.click();
      }
    },
    handleProjectFile() {
      let file = this.$refs.projectFileRef.files[0];
      let fileUploadTip = this.$refs.upLoadTipRef;
      let submitFileBtn = this.$refs.submitFileBtnRef;
      if (file.size > 20 * 1024 * 1024) {
        //上传的文件不可大于20M
        //判断文件类型和大小
        this.$message.error("文件过大，请重新上传");
        return false;
      }
      // let reg = /^([application/pdf]|[image/*]|[application/vnd.openxmlformats\-officedocument.wordprocessingml.document]|[application/msword])+$/;
      // let regResult = reg.test(file.type);
      let reg = /\.(jpg|png|jpeg|doc|docx|pdf|zip|rar|7z)$/;
      var regResult = reg.test(file.name);
      if (!regResult) {
        // console.log("正则匹配", reg.test(file.name));
        // console.log("文件类型错误，请重新上传");
        this.$message.error("文件类型错误，请重新上传");
        return false;
      }
      this.resumeData.projectFile = file;
      fileUploadTip.innerText = file.name;
      submitFileBtn.innerText = "更换文件";
    },
    testBtn() {
      this.resumeData.name = "雷盐透";
      this.resumeData.phone = "12345678911";
      this.resumeData.sex = "女";
      this.resumeData.grade = "大二";
      this.resumeData.department = "信息工程学院";
      this.resumeData.major = "电子信息类";
      this.resumeData.QQ = "1654189654";
      this.resumeData.mailaddress = "5645165@qq.com";
      this.resumeData.address = "西四";
      this.resumeData.studentId = "3119002159";
      this.resumeData.intensionGroup = ["1", "3"];
      this.resumeData.adjustment = "否";
      this.resumeData.joinReason = "无";
      this.resumeData.advantage = "无优势";
      this.resumeData.disadvantage = "无劣势";
      this.resumeData.futurePlanning = "无未来规划";
      this.resumeData.workExperience = [
        {
          associationName: "社团名称",
          workDate: ["2020-02-01", "2020-03-01"],
          postName: "职位",
          job: "工作",
          // isShowAssociationName: false,
          // isShowWorkDate: false,
          // isShowPostName: false,
        },
        {
          associationName: "社团名称2",
          workDate: ["2020-02-01", "2020-04-01"],
          postName: "职位2",
          job: "工作2",
          // isShowAssociationName: false,
          // isShowWorkDate: false,
          // isShowPostName: false,
        },
      ];
      this.resumeData.disposableTime = "可支配时间";
      this.resumeData.schoolAward = "无奖励";
      this.resumeData.selfIntroduction = "这是自我介绍";
      this.resumeData.codeKeyInput = "";
      this.resumeData.resumeId = "";
    },
    dialogContinue() {
      if (this.$refs.projectFileRef) {
        this.$refs.projectFileRef.click();
        this.dialogVisible = false;
      }
    },
    routerSearchResume() {
      this.$router.push({ name: "SearchResume" });
    },
  },
  beforeRouteEnter(to, from, next) {
    if (
      from.name == "SearchResume" &&
      sessionStorage.getItem("searchResumeData") == "searchResumeData"
    ) {
      // 查询到简历了
      next((vm) => {
        sessionStorage.setItem("searchResumeData", "");
        let oldResumeData = vm.$store.state.resumeData;
        vm.resumeState = oldResumeData.resumeState;
        let resumeData = vm.resumeData;
        resumeData.name = oldResumeData.name;
        resumeData.phone = oldResumeData.phone;
        resumeData.sex = oldResumeData.sex;
        resumeData.grade = oldResumeData.grade;
        resumeData.department = oldResumeData.department;
        resumeData.major = oldResumeData.major;
        resumeData.QQ = oldResumeData.qq;
        resumeData.mailaddress = oldResumeData.mailaddress;
        resumeData.address = oldResumeData.address;
        resumeData.studentId = oldResumeData.studentId;
        resumeData.intensionGroup = oldResumeData.intensionGroup;
        resumeData.adjustment = oldResumeData.adjustment;
        resumeData.joinReason = oldResumeData.joinReason;
        resumeData.advantage = oldResumeData.advantage;
        resumeData.disadvantage = oldResumeData.disadvantage;
        resumeData.futurePlanning = oldResumeData.futurePlanning;
        resumeData.workExperience = JSON.parse(oldResumeData.workExperience);
        resumeData.disposableTime = oldResumeData.disposableTime;
        resumeData.schoolAward = oldResumeData.schoolAward;
        resumeData.selfIntroduction = oldResumeData.selfIntroduction;
        resumeData.resumeId = oldResumeData.resumeId;
      });
      // let resumeData = this.$store.state.resumeData;
      // console.log(resumeData);
    }
    next();
  },
  created() {
    // 发送请求，获取验证码
    this.changeCodeImg();
    // // let _this = this;
    // // this.$axios({
    // //   method: "get",
    // //   url:
    // //     "http://8.129.163.145:10012/stu/getCode?codeKey=" +
    // //     _this.resumeData.codeKey,
    // // })
    // resume_api
    //   .getCode({ codeKey: this.resumeData.codeKey })
    //   .then((res) => {
    //     this.codeData = res;
    //     if (res.state) {
    //       //成功获取验证码
    //       this.resumeData.codeKey = res.codeKey;
    //       this.codeImgString = res.imgString;
    //     }
    //   })
    //   .catch(function (err) {
    //     console.log("get CodeImg error");
    //   });
  },
};
</script>

<style scoped lang='less'>
@import "../assets/css/resume.less";
</style>