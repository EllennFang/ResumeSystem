<template>
  <div id="">
    <!-- 卡片视图区域 -->
    <el-card>
      <div class="deadline">
        <div class="deadline-info">简历填报截止时间：{{ deadline }}</div>
        <el-date-picker
          ref="deadlinePickerRef"
          v-model="newDeadline"
          type="datetime"
          placeholder="修改简历填报截止时间"
          :editable="false"
          :picker-options="deadlinePickerOptions"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
        >
        </el-date-picker>
        <el-popconfirm
          title="是否确认修改简历填报截止时间"
          @confirm="editDeadline"
        >
          <el-button slot="reference">确认修改</el-button>
        </el-popconfirm>
      </div>
      <el-input
        maxlength="20"
        v-model.trim="searchAdminsValue"
        @input="searchChange"
        @keyup.enter.native="searchAdmins"
        placeholder="请输入账号"
        class="input-with-select"
        clearable
      >
        <el-button
          slot="append"
          icon="el-icon-search"
          @click="searchAdmins"
        ></el-button>
      </el-input>
      <el-button @click="addAdmin">添加管理员</el-button>

      <!-- 管理员账号列表区域 -->
      <el-table border stripe :data="renderAdminList">
        <!-- <el-table-column label="id" prop="id"></el-table-column> -->
        <el-table-column type="index"></el-table-column>
        <el-table-column label="账号" prop="username"></el-table-column>
        <el-table-column label="密码" prop="password"></el-table-column>
        <el-table-column label="权限" prop="role"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <div class="operation-admin">
              <el-button
                type="primary"
                icon="el-icon-edit"
                @click="editAdmin(scope.row)"
              ></el-button>
              <el-popconfirm
                confirm-button-text="确定"
                cancel-button-text="不用了"
                icon="el-icon-info"
                icon-color="red"
                title="确定删除嘛?"
                @confirm="deleteAdmin(scope.row)"
              >
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  v-if="scope.row.role != 'root'"
                  slot="reference"
                ></el-button>
              </el-popconfirm>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加/编辑管理员的 dialog Form -->
    <el-dialog
      center
      :close-on-click-modal="false"
      :title="dialogInfo.title"
      :visible.sync="dialogInfo.editAdminDialogVisible"
      @close="closeDialog"
    >
      <el-form
        :model="editAdminForm"
        :rules="addAdminRules"
        ref="addAdminFormRef"
      >
        <el-form-item
          label="账号"
          prop="username"
          :label-width="dialogInfo.editAdminDialogLabelWidth"
        >
          <el-input
            v-model.trim="editAdminForm.username"
            maxlength="20"
            @input="handleAdminFormChange"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="密码"
          prop="password"
          :label-width="dialogInfo.editAdminDialogLabelWidth"
        >
          <el-input
            v-model.trim="editAdminForm.password"
            maxlength="20"
            @input="handleAdminFormChange"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleDialogCancel">取 消</el-button>
        <el-button type="primary" @click="handleDialogSubmit('addAdminFormRef')"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {modifyAdminInfo} from '../../network/admin'
export default {
  name: "",
  data() {
    return {
      adminList: [], // 管理员列表
      renderAdminList: [], // 用于渲染
      searchAdminsValue: "", // 根据账号查找管理员输入框的绑定值
      editAdminForm: {
        // 添加/编辑的管理员信息
        id: "",
        username: "",
        password: "",
      },
      temEditAdminInfo: {}, // 临时保存管理员信息，用于验证是否有修改管理员账号信息
      dialogInfo: {
        // dialogInfo 的部分渲染信息
        operation: "", // 1 表示添加管理员，2 表示编辑管理员
        title: "",
        editAdminDialogVisible: false,
        editAdminDialogLabelWidth: "70px",
      },
      addAdminRules: {
        // 表单验证规则
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            pattern: /^([0-9]|[a-z])*$/i,
            message: "请输入正确的账号",
            trigger: "blur",
          },
          {
            pattern: /^([0-9]|[a-z])*$/i,
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
      },
      deadline: "",
      newDeadline: "",
      deadlinePickerOptions: {
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "明天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周后",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一个月后",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() + 3600 * 1000 * 24 * 31);
              picker.$emit("pick", date);
            },
          },
        ],
      },
      popConfirmTitle: "修改为",
    };
  },
  methods: {
    // 获取所有管理员信息
    getAdmins() {
      // 发送get请求
      modifyAdminInfo.getAdmins()
        .then((res) => {
          if (!res.state) {
            // 出错
            this.$message.error(res.msg);
            return;
          }
          // 获取成功
          this.adminList = res.data;
          this.renderAdminList = res.data;
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取管理员失败");
        });
    },
    // 获取简历填报截止时间
    getDeadline() {
      // 发送get请求
      modifyAdminInfo.getDeadline()
        .then((res) => {
          if (!res.state) {
            // 出错
            this.$message.error(res.msg);
            return;
          }
          this.deadline = res.deadline;
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("获取截至日期失败");
        });
    },
    // 修改截止时间
    editDeadline() {
      console.log("修改截止时间");
      // 判断是否有选择截止时间
      if (!this.newDeadline) {
        this.$message.warning("未选择简历填报截止时间！");
        return;
      }
      // 发送请求，修改简历填报截止时间
      let formData = new FormData();
      formData.append("deadline", this.newDeadline);
      // 发送put请求
      modifyAdminInfo.setDeadline(formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        }
      })
        .then((res) => {
          if (!res.state) {
            // 出错
            this.$message.error(res.msg);
            return;
          }
          // 修改成功
          this.$message.success(res.msg);
          this.getDeadline();
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("修改失败");
        });
    },
    // 搜索输入框内容变化
    searchChange(searchAdminsValue) {
      // 输入框内容为空时，重新渲染所有管理员信息
      if (searchAdminsValue == "" && this.renderAdminList != this.adminList) {
        this.renderAdminList = this.adminList;
      }
    },
    // 根据账号搜索管理员
    searchAdmins() {
      this.renderAdminList = [];
      let reg = new RegExp(this.searchAdminsValue, "g");
      this.adminList.forEach((item) => {
        if (reg.test(item.username)) {
          this.renderAdminList.push(item);
        }
      });
    },
    // 点击添加管理员
    addAdmin() {
      // 渲染 Dialog 信息
      this.dialogInfo.operation = "1";
      this.dialogInfo.title = "添加管理员";
      this.dialogInfo.editAdminDialogVisible = true;
    },
    // 编辑管理员账号信息
    editAdmin(adminInfo) {
      this.temEditAdminInfo = adminInfo; // 临时保存管理员信息，用于验证是否有修改管理员账号信息
      // 渲染 Dialog 信息
      this.dialogInfo.operation = "2";
      this.dialogInfo.title = "修改管理员账号信息";
      this.editAdminForm.username = adminInfo.username;
      this.editAdminForm.password = adminInfo.password;
      this.dialogInfo.editAdminDialogVisible = true;
      // 设置表单数据 id
      this.editAdminForm.id = adminInfo.id;
    },
    // 删除管理员账号
    deleteAdmin(adminInfo) {
      let formData = new FormData();
      formData.append("id", adminInfo.id);
      // 发送delete请求
      modifyAdminInfo.deleteAdmin()
        .then((res) => {
          if (!res.state) {
            // 删除失败
            this.$message.error(res.msg);
            return;
          }
          this.$message.success("删除成功");
          // 重新发送请求获取管理员列表
          this.getAdmins();
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("删除失败");
        });
    },
    // Dialog 点击确定事件，添加/编辑管理员
    handleDialogSubmit(formName) {
      let addAdminValid = false;
      this.$refs[formName].validate((valid) => {
        addAdminValid = valid;
        console.log(valid);
      });
      if (!addAdminValid) {
        return;
      }
      let editAdminForm = this.editAdminForm;
      let formData = new FormData();
      if (this.dialogInfo.operation == "1") {
        // 为 1 时，添加管理员，为 2 编辑管理员信息
        // 添加管理员
        formData.append("username", editAdminForm.username);
        formData.append("password", editAdminForm.password);
        // 发送post请求
        modifyAdminInfo.addAdmin(formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          }
        })
          .then((res) => {
            if (!res.state) {
              // 添加失败
              this.$message.error(res.msg);
              return;
            }
            this.$message.success("管理员添加成功");
            // 关闭 Dialog
            this.dialogInfo.editAdminDialogVisible = false;
            // 重新发送请求获取管理员列表
            this.getAdmins();
          })
          .catch((err) => {
            console.log(err);
            this.$message.error("管理员添加失败");
          });
      } else if (this.dialogInfo.operation == "2") {
        // 编辑管理员
        let temEditAdminInfo = this.temEditAdminInfo;
        // 下面这个 if 用来验证账号密码是否有修改
        if (
          temEditAdminInfo.username == editAdminForm.username &&
          temEditAdminInfo.password == editAdminForm.password
        ) {
          this.$message.warning("账号密码未修改");
          return;
        }
        formData.append("username", editAdminForm.username);
        formData.append("password", editAdminForm.password);
        formData.append("id", this.editAdminForm.id);
        // 发送put请求
        modifyAdminInfo.updateAdmin(formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          }
        })
          .then((res) => {
            if (!res.state) {
              // 编辑失败
              this.$message.error(res.msg);
              return;
            }
            this.$message.success("修改成功");
            // 关闭 Dialog
            this.dialogInfo.editAdminDialogVisible = false;
            // 重新发送请求获取管理员列表
            this.getAdmins();
          })
          .catch((err) => {
            console.log(err);
            this.$message.error("修改失败");
          });
      } else {
        this.$message.error("修改失败");
      }
    },
    // 账号密码输入框内容变化出发
    handleAdminFormChange(e) {
      console.log(e);
      let reg = /[a-z]|[A-Z]|[0-9]/g;
    },
    // Dialog 点击取消事件
    handleDialogCancel() {
      this.dialogInfo.editAdminDialogVisible = false;
    },
    // Dialog 关闭的回调
    closeDialog() {
      this.editAdminForm.id = "";
      this.editAdminForm.username = "";
      this.editAdminForm.password = "";
    },
  },
  // 路由导航守卫
  beforeRouteEnter(to, from, next) {
    let role = window.localStorage.getItem("role");
    if (role == "root") {
      return next();
    }
    next("/QueryByGroup");
  },
  created() {
    this.getAdmins(); // 发送请求获取管理员列表
    this.getDeadline(); // 发送请求获取简历填报截止时间
  },
};
</script>

<style lang='less' scoped>
@import "../../assets/css/children/AddAdmin.less";
</style>