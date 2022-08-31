let frequentlyCounter = 0;
function frequentlyCount(_this) {// 15 内秒允许点击 6 次
  frequentlyCounter++;
  if (frequentlyCounter > 6) {
    _this.$message.warning("操作过于频繁");
    return true;
  }
  if (frequentlyCounter == 1) {
    setTimeout(() => {
      frequentlyCounter = 0;
    }, 15 * 1000);
  }
  // clearTimeout(changeCodeTimtOut);
  // changeCodeTimtOut = 
  return false;
}

// 节流
function throttle(fn, delay = 300) {
  let timer = null;
  let timeStamp = new Date();
  return () => {
    let args = arguments;  //取得传入参数
    if (new Date() - timeStamp > delay) {
      timeStamp = new Date();
      timer = setTimeout(function () {
        fn.apply(this, args);
      }, delay);
    }

  }
}

export default {
  //抛出方法名
  frequentlyCount,
  throttle
}