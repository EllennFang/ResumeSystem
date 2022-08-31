<template>
  <div id="">
    <!-- 选择区域 -->
    <div class="group-choose">
      <div
        :class="['choose-button', { active: isActiveIndex == index }]"
        @click="handleButtonClick($event)"
        v-for="(item, index) in buttonTabsList"
        :key="item"
        :data-index="index"
      >
        {{ item }}
      </div>
    </div>
  </div>
</template>

<script>
/**
 * 需要入数组 buttonTabsList
 * **/
export default {
  name: "",
  props: ['buttonTabsList', 'index'],
  data() {
    return {
      isActiveIndex: "0",
    };
  },
  watch: {
    index: function (newValue, oldValue){
      this.isActiveIndex = newValue
    }
  },
  methods: {
    handleButtonClick(e) {
      this.isActiveIndex = e.target.dataset.index;
      console.log(e);
      this.$emit("buttonTabs", this.isActiveIndex);
    },
  },
};
</script>

<style lang='less' scoped>
.group-choose {
  .choose-button {
    padding: 12px 20px;
    margin-right: 20px;
    display: inline-block;
    box-sizing: border-box;
    text-align: center;
    line-height: 1;
    font-weight: 500;
    color: #606266;
    cursor: pointer;
    border: 1px solid #dcdfe6;
    border-radius: 4px;
  }
  .choose-button:active {
    color: #3a8ee6;
    border: 1px solid #3a8ee6;
  }
  .active {
    background: #40B6FF;
    color: white;
    border: 1px solid #40B6FF;
    // color: #40B6FF;
    // border: 1px solid #40B6FF;
  }
}
</style>