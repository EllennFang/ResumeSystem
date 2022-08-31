<!--
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-03-27 23:50:50
 * @LastEditors: Youngletter
 * @LastEditTime: 2021-09-20 10:25:19
-->
<template>
  <div id="">
    <el-card>
      <!-- 日志区域 -->
      <el-table border stripe :data="logsList">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="管理员账号" prop="username"></el-table-column>
        <el-table-column label="操作时间" prop="time"></el-table-column>
        <el-table-column label="具体操作信息" prop="info"></el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <div class="pagination">
        <el-pagination
          hide-on-single-page
          :page-size="20"
          :page-count="paginationInfo.pageCount"
          :current-page="paginationInfo.currentPage"
          @current-change="handlePagesChange"
        >
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
import {getLog} from '../../network/admin'
export default {
  name: "",
  data() {
    return {
      paginationInfo: {
        // 分页信息
        pageCount: 1, // 总页数，默认 1
        currentPage: 1, // 当前页数
      },
      logsList: [],
    };
  },
  methods: {
    // 查询日志信息
    queryLogs() {
      let queryInfo = {
        page: this.paginationInfo.currentPage
      };
      // 发送get请求
      getLog(queryInfo)
        .then((res) => {
          if (!res.state) {
            this.$message.error(res.msg);
            console.log(res.msg);
            return false;
          }
          this.paginationInfo.pageCount = res.pages;
          this.logsList = res.logs;
        })
        .catch((err) => {
          console.log(err);
          this.$message.error('获取日志信息失败')
        });
    },
    // 切换页数
    handlePagesChange(currentPage) {
      this.paginationInfo.currentPage = currentPage;
      // 查询日志
      this.queryLogs();
    },
  },
  created() {
    this.queryLogs();
  },
};
</script>

<style lang='less' scoped>
@import "../../assets/css/global.less";
@import "../../assets/css/children/ManagerPublic.less";
</style>