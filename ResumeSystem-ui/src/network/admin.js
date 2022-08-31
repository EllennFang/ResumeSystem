/*
 * @Descripttion: 
 * @version: 
 * @Author: YoungLetter
 * @Date: 2021-09-06 22:44:57
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2021-09-21 23:52:13
 */
import { requestAdmin, getMethod, postMethod, deleteMethod, putMethod } from './request'

// 基于requestAdmin 的各种不同请求方法
const getData = getMethod(requestAdmin)
const postData = postMethod(requestAdmin)
const deleteData = deleteMethod(requestAdmin)
const putData = putMethod(requestAdmin)

// 登陆API
export const login = (data, config) => postData('/login', data, config)

// 不同种类查找简历API
export const queryByStuId = (params) => getData('/queryByStuId', params)
export const queryByResumeState = (params) => getData('/queryByResumeState', params)
export const queryByName = (params) => getData('/queryByName', params)
export const queryByGroup = (params) => getData('/queryByGroup', params)
export const getIntensionGroup = (params) => getData('/queryByGroup', params)
export const queryResumeMethods = { queryByStuId, queryByResumeState, queryByName, queryByGroup, getIntensionGroup }

// 删除简历API
export const deleteResumeById = (params) => deleteData('/deleteResume', params)

// 简历详情页API
const saveComment = (data, config) => postData('/saveComment', data, config)
const updateResumeState = (data, config) => putData('/updateResumeState', data, config)
export const ModifyResume = { saveComment, updateResumeState }

// 查看日志页API
export const getLog = (params) => getData('/getLog', params)

// 添加管理员界面API
const getAdmins = (params) => getData('/getAdmins', params)
const getDeadline = (params) => getData('/getDeadline', params)
const setDeadline = (data, config) => putData('/setDeadline', data, config)
const deleteAdmin = (params) => deleteData('/deleteAdmin', params)
const addAdmin = (data, config) => postData('/addAdmin', data, config)
const updateAdmin = (data, config) => putData('/updateAdmin', data, config)
export const modifyAdminInfo = { getAdmins, getDeadline, setDeadline, deleteAdmin, addAdmin, updateAdmin }

// 短信模板API
const getTemplates = (params) => getData('/getTemplates', params);
const saveTemplate = (params) => postData('/saveTemplate', params);
const deleteTemplate = (params) => deleteData('/deleteTemplate', params);
const updateSMSState = (params) => putData('/updateSMSState', params);
export const api_SMSTemplate = { getTemplates, saveTemplate, deleteTemplate,updateSMSState };