/*
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-09-06 22:44:48
 * @LastEditors: Youngletter
 * @LastEditTime: 2021-09-20 11:12:40
 */
import { requestStudent, postMethod } from './request'
import { Message } from "element-ui"

const postData = postMethod(requestStudent)

function formateURLOnlyGet(link, json) {
  let url = link;
  let data = Object.entries(json);
  if (data.length) {
    url += url.indexOf("?") == -1 ? "?" : "";
    url += Object.entries(data)
      .map(item => {
        return item[1].join("=");
      })
      .join("&");
  }
  return url;
}

export async function getData(url, params = {}, config = {}) {
  try {
    const res = await requestStudent
      .get(formateURLOnlyGet(url, params), config);
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

// export async function postData(url, params = {}, config = {}) {
//   try {
//     const res = await requestStudent
//       .post(url, params, config);
//     if (res.status == 200) {
//       return res.data;
//     }
//     Message.error("提交失败");
//     return res;
//   } catch (error) {
//     Message.error("error");
//     return error.response;
//   }
// }

// 演示： submitResume(formData, {headers: { "Content-Type": "multipart/form-data" }}).then(res=>res).catch(err=>err);
export const getCode = (params, config) => getData("/getCode", params, config);
export const submitResume = (params, config) => postData("/resume", params, config);
export const checkByResumeId = (params, config) => getData("/checkByResumeId", params, config);
export const checkByStudentId = (params, config) => getData("/checkByStudentId", params, config);

export const resume_api = { getCode, submitResume };
export const checkResume_api = { getCode, checkByResumeId, checkByStudentId };