/*
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-09-06 22:44:42
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2021-09-22 18:01:22
 */
import axios from 'axios'
import { Message } from "element-ui"

// 对admin的请求
const requestAdmin = axios.create({
  baseURL: 'http://8.129.163.145:10012/admin',
  timeout: 5000,
})

// 请求拦截器，在请求前加上token
requestAdmin.interceptors.request.use(
  config => {
    let requestPath = config.url;
    let reg = /(\/stu)|(\/admin\/login)/
    if (!reg.test(requestPath)) {
      // 不是公共接口，添加 token
      config.headers.token = localStorage.getItem("token");
    }
    return config;
  },
  err => {
    return Promise.reject(err)
  }
)
requestAdmin.interceptors.response.use(
  res => {
    if (res.status === 200 && res.data.code === 403) {
      localStorage.removeItem('token');
      document.location.href = document.location.origin + '/ManagerLogin';
      localStorage.setItem('errorMsg', res.data.msg);
    }
    return res;
  },
  err => {
    return Promise.reject(err)
  }
)

export { requestAdmin }

// 对stu的请求
export const requestStudent = axios.create({
  baseURL: 'http://8.129.163.145:10012/stu',
  timeout: 5000,
})

// method 为get
export const getMethod = (request) => {
  return async (url, params = {}) => {
    try {
      const res = await request.get(url, {
        params,
      })
      if (res.status == 200) {
        return res.data;
      }
      Message.error("请求失败");
      return res;
    } catch (error) {
      Message.error("error");
      return error.response;
    }
  }
}

// method 为post
export const postMethod = (request) => {
  return async (url, data = {}, config = {}) => {
    try {
      const res = await request.post(url, data, config);
      if (res.status == 200) {
        return res.data;
      }
      Message.error("提交失败");
      return res;
    } catch (error) {
      Message.error("error");
      return error.response;
    }
  }
}


// method 为put
export const putMethod = (request) => {
  return async (url, data = {}, config = {}) => {
    try {
      const res = await request.put(url, data, config);
      if (res.status == 200) {
        return res.data;
      }
      Message.error("提交失败");
      return res;
    } catch (error) {
      Message.error("error");
      return error.response;
    }
  }
}

// method 为delete
export const deleteMethod = (request) => {
  return async (url, params = {}) => {
    try {
      const res = await request.delete(url, {
        params,
      })
      if (res.status == 200) {
        return res.data;
      }
      Message.error("请求失败");
      return res;
    } catch (error) {
      Message.error("error");
      return error.response;
    }
  }
}


