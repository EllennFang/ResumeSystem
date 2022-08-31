<template>
  <div id="">
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 组别选择区域 -->
      <ButtonTabs @buttonTabs="handleButtonTabs" :buttonTabsList="buttonTabsList" :index="index"></ButtonTabs>
      <!-- 简历列表区域 -->
      <el-table border stripe :data="resumeList" >
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="name" show-overflow-tooltip></el-table-column>
        <el-table-column label="学号" prop="studentId"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="年级" prop="grade"></el-table-column>
        <el-table-column label="意向组别" width="310%">
          <template slot-scope="scope">
            <span
              class="intentsion-group"
              v-for="item in scope.row.intensionGroup"
              :key="item.resumeId"
            >
              <el-tag type="success" disable-transitions>{{ item }}</el-tag>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="是否接受调剂">
          <template slot-scope="scope">
            <div class="resume-state">
              <div v-if="scope.row.adjustment == '是'" style="color: #67c23a">
                是
              </div>
              <div
                v-else-if="scope.row.adjustment == '否'"
                style="color: #f56c6c"
              >
                否
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="简历状态">
          <template slot-scope="scope">
            <div class="resume-state">
              <div :style="{color: scope.row.resumeState=='未筛选'||scope.row.resumeState=='未通过面试'||scope.row.resumeState == '未通过筛选'?'#f56c6c':'#67c23a'}">
                {{scope.row.resumeState}}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="简历详情">
          <template slot-scope="scope">
            <span @click="transmitToParent(scope.row)" class="resume-detail"
              >查看详情</span
            >
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-popconfirm
              confirm-button-text="确定"
              cancel-button-text="不用了"
              icon="el-icon-info"
              icon-color="red"
              title="确定删除嘛?"
              @confirm="deleteResume(scope.row)"
            >
              <span class="resume-delete" slot="reference">删除</span>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <div class="pagination">
        <el-pagination
          hide-on-single-page
          @current-change="handlePagesChange"
          :current-page="paginationInfo.currentPage"
          :page-size="10"
          layout="total, prev, pager, next"
          :total="paginationInfo.total"
        >
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
import ButtonTabs from "../../components/ButtonTabs.vue";
export default {
  components: {
    ButtonTabs
  },
  data() {
    return {
      SMSState: "0",
      index: "0",
      resumeList: [],
      buttonTabsList: ["未发送","已发送","发送失败"],
      paginationInfo: {// 分页信息
        pageCount: 1,// 总页数，默认 1
        currentPage: 1,// 当前页数
        total: 1,
      },
    };
  },
  methods: {
    // ButtonTabs 组件发送的事件，点击按钮
    handleButtonTabs(index) {
      // 清空分页信息
      this.paginationInfo.currentPage = 1;
      this.SMSState = index;
      this.queryBySMSState();
    },
    // 点击短信发送状态查询查询
    queryBySMSState() {
      let queryInfo = {
        SMSState:this.SMSState,
        page: this.paginationInfo.currentPage,
      };
      this.$axios({
        method: "get",
        url: "http://8.129.163.145:10012/admin/queryBySMSState",
        params: queryInfo,
      })
        .then((res) => {
          res = res.data;
          if (!res.state) {
            this.$message.error(res.msg);
            return;
          }
          // 成功获取简历列表
          // 对数据进行处理
          let intensionGroupValueArr = ["软件组", "硬件组", "设计组", "算法组", "创想联盟组"];
          let resumeStateValueArr = [
            "未筛选",
            "通过筛选",
            "通过面试",
            "未通过面试",
            '未通过筛选',
          ];
          res.data = res.data.map((item1) => {
            // 如果仅修改数组或对象，引用数据类型，可以不 return ，也不用接收新数组
            // item1.intensionGroup = JSON.parse(item1.intensionGroup);
            item1.intensionGroup = item1.intensionGroup.split(",");
            // 将 intensionGroup 数组中的数字替换为 软件组、硬件组、设计组
            item1.intensionGroup = item1.intensionGroup.map((item2) => {
              item2 = intensionGroupValueArr[item2 - 1];
              return item2;
            });
            // 将 resumeState 的数字替换为 未筛选、通过筛选、未通过面试、通过面试
            item1.resumeState = resumeStateValueArr[item1.resumeState];
            return item1;
          });
          this.resumeList = res.data;
          this.paginationInfo.pageCount = res.pages;
          this.paginationInfo.total = res.total
        })
        .catch((err) => {
          this.$message.error("简历查询失败");
          console.log(err);
        });
    },
    // 查看详情，触发父组件中的事件，并将参数传递过去
    transmitToParent(resumeInfo) {
      this.$emit('transmitResumeInfo', resumeInfo);// 触发 Manager 的事件，并传递参数
      this.$router.push({name:"ExamineResume",params: {state: true}, query: {currentPage: this.paginationInfo.currentPage, SMSState: this.SMSState, total: this.paginationInfo.total}});
    },
    // 删除简历
    deleteResume(resumeInfo) {
      let queryInfo = {
        id: resumeInfo.id,
      };
      this.$axios({
        method: "delete",
        url: "http://8.129.163.145:10012/admin/deleteResume",
        params: queryInfo,
      })
        .then((res) => {
          res = res.data;
          if (!res.state) {
            this.$message.error(res.msg);
            return false;
          }
          this.$message.success(res.msg);
          this.queryBySMSState();
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("删除失败！");
        });
    },
    // 切换页数
    handlePagesChange(currentPage) {
      this.paginationInfo.currentPage = currentPage;
      // 根据组别查询简历
      this.queryBySMSState();
    },
  },
  // created() {
  //   this.queryBySMSState();
  // },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (from.name === 'ExamineResume') {
        const {SMSState, currentPage, total} = from.query
        vm.SMSState = SMSState
        vm.index = SMSState
        vm.paginationInfo.currentPage = currentPage
        vm.paginationInfo.total = total
      }
      vm.queryBySMSState();
    });
  }
};
</script>

<style lang='less' scoped>
@import "../../assets/css/global.less";
@import "../../assets/css/children/ManagerPublic.less";
</style>