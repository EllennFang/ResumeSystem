<template>
  <div id="">
    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 组别选择区域 -->
      <!-- 选择区域 -->
      <div class="group-choose">
        <div
          :class="['choose-button', { active: intensionGroup[index].isActive }]"
          @click="handleButtonTabs(index)"
          v-for="(item, index) in buttonTabsList"
          :key="item"
          :data-index="index"
        >
          {{ item }}
        </div>
      </div>
      <el-table border stripe :data="resumeList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
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
              <div v-else style="color: #f56c6c">否</div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="简历状态">
          <template slot-scope="scope">
            <div class="resume-state">
              <!-- <div v-if="scope.row.resumeState == '0'" style="color: #f56c6c">
                未筛选
              </div>
              <div
                v-else-if="scope.row.resumeState == '1'"
                style="color: #67c23a"
              >
                通过筛选
              </div>
              <div
                v-else-if="scope.row.resumeState == '2'"
                style="color: #f56c6c"
              >
                未通过面试
              </div>
              <div
                v-else-if="scope.row.resumeState == '3'"
                style="color: #67c23a"
              >
                通过面试
              </div> -->
              <!-- <div
                v-if="
                  scope.row.resumeState == '未筛选' ||
                  scope.row.resumeState == '未通过面试'
                "
                style="color: #f56c6c"
              >
                {{ scope.row.resumeState }}
              </div>
              <div
                v-else-if="
                  scope.row.resumeState == '通过筛选' ||
                  scope.row.resumeState == '通过面试'
                "
                style="color: #67c23a"
              >
                {{ scope.row.resumeState }}
              </div> -->
              <div
                :style="{
                  color:
                    scope.row.resumeState == '未筛选' ||
                    scope.row.resumeState == '未通过面试' ||
                    scope.row.resumeState == '未通过筛选'
                      ? '#f56c6c'
                      : '#67c23a',
                }"
              >
                {{ scope.row.resumeState }}
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
import { deleteResumeById, queryResumeMethods} from '../../network/admin'

export default {
  data() {
    return {
      intensionGroup: [
        {
          isActive: false,
        },
        {
          isActive: false,
        },
        {
          isActive: false,
        },
        {
          isActive: false,
        },
        {
          isActive: false,
        },
      ],
      resumeList: [],
      buttonTabsList: ["软件组", "硬件组", "设计组", "算法组", "创想联盟组"],
      paginationInfo: {
        // 分页信息
        pageCount: 1, // 总页数，默认 1
        currentPage: 1, // 当前页数
        total: 1,
      },
    };
  },
  methods: {
    // 点击按钮
    handleButtonTabs(index) {
      // 清空分页信息
      this.paginationInfo.currentPage = 1;
      // this.intensionGroup = ++index;
      this.intensionGroup[index].isActive =
        !this.intensionGroup[index].isActive;
      this.queryResume();
    },
    // 查询简历
    async queryResume() {
      try {
        // 根据组别查询简历发送请求
        // 处理要发送的数据
        let intensionGroup = "";
        this.intensionGroup.forEach((item, index) => {
          if (item.isActive) {
            if (intensionGroup) {
              intensionGroup += ",";
            }
            intensionGroup += ++index;
          }
        });
        let queryInfo = {
          intensionGroup,
          page: this.paginationInfo.currentPage,
        };
        // 发送get请求
        let res = await queryResumeMethods.queryByGroup(queryInfo)
        let {pages, total} = res
        // 当 当前页数大于总页数，回到第一页
        if (this.paginationInfo.currentPage > pages) {
          queryInfo.page = 1;
          this.paginationInfo.currentPage = 1;
          // 发送 get 请求
          res = await queryResumeMethods.queryByGroup(queryInfo)
          pages = res.pages
        }
        if (!res.state) {
          // 出错
          this.$message.error(res.msg);
          return;
        }
        // 成功获取简历列表
        // 对数据进行处理
        let intensionGroupValueArr = [
          "软件组",
          "硬件组",
          "设计组",
          "算法组",
          "创想联盟组",
        ];
        let resumeStateValueArr = [
          "未筛选",
          "通过筛选",
          "通过面试",
          "未通过面试",
          "未通过筛选",
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
        this.paginationInfo.pageCount = res.pages;
        this.paginationInfo.total = total
        this.resumeList = res.data;
      } catch (err) {
        console.log(err.message);
      }
    },
    // 查看详情，触发父组件中的事件，并将参数传递过去
    transmitToParent(resumeInfo) {
      this.$emit("transmitResumeInfo", resumeInfo); // 触发 Manager 的事件，并传递参数
      this.$router.push({ name: "ExamineResume", params: { state: true }, query: {currentPage: this.paginationInfo.currentPage, intensionGroup: this.intensionGroup, total: this.paginationInfo.total} });
    },
    // 删除简历
    deleteResume(resumeInfo) {
      let queryInfo = {
        id: resumeInfo.id,
      };
      // 发送delete请求
      deleteResumeById(queryInfo)
        .then((res) => {
          if (!res.state) {
            this.$message.error(res.msg);
            return false;
          }
          this.$message.success(res.msg);
          this.queryResume();
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
      this.queryResume();
    },
  },
  created() {},
  // mounted() {
  //   this.queryResume();
  // },
  beforeRouteEnter(to, from, next) {
    next((vm) => {
      if (from.name === 'ExamineResume') {
        const {intensionGroup, currentPage, total} = from.query
        vm.intensionGroup = intensionGroup
        vm.paginationInfo.currentPage = currentPage
        vm.paginationInfo.total = total
      }
      vm.queryResume()
    });
  }
};
</script>

<style lang='less' scoped>
@import "../../assets/css/global.less";
@import "../../assets/css/children/ManagerPublic.less";
</style>