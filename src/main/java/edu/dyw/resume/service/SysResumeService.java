package edu.dyw.resume.service;

import edu.dyw.resume.entity.AjaxResult;
import edu.dyw.resume.entity.ResumeBody;
import edu.dyw.resume.entity.User;
import edu.dyw.resume.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author miaomiao
 */
@Service
@Transactional
public class SysResumeService {

    @Autowired
    private AjaxResult ajaxResult;

    @Autowired
    private UserMapper userMapper;

     /**
     * 简历保存
     */
    public AjaxResult save(ResumeBody resumeBody) {
        //将RusumeBody的值存入User
        User student = setUser(resumeBody);

        userMapper.insert(student);
        ajaxResult.setState(true);
        ajaxResult.setMsg("保存成功");
        System.out.println("保存成功");
        return ajaxResult;
    }

    /**
     * 简历修改
     */
    public AjaxResult updateById(ResumeBody resumeBody,Long id){

        //将RusumeBody的值存入User,同时存入id
        User student = setUser(resumeBody);
        student.setId(id);
        //根据id修改简历信息
        userMapper.updateById(student);
        ajaxResult.setState(true);
        ajaxResult.setMsg("修改成功");
        System.out.println("修改成功");
        return ajaxResult;
    }


    public Map<String,Object> deleteResume(Long id){
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            userMapper.deleteById(id);
            hashMap.put("state",true);
            hashMap.put("msg","删除成功");
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("state",false);
            hashMap.put("msg","删除失败");
            return hashMap;
        }
    }

    //将RusumeBody的值存入User
    private User setUser(ResumeBody resumeBody){
        User student = new User();
        student.setName(resumeBody.getName());
        student.setSex(resumeBody.getSex());
        student.setPhone(resumeBody.getPhone());
        student.setGrade(resumeBody.getGrade());
        student.setDepartment(resumeBody.getDepartment());
        student.setMajor(resumeBody.getMajor());
        student.setQQ(resumeBody.getQQ());
        student.setMailaddress(resumeBody.getMailaddress());
        student.setAddress(resumeBody.getAddress());
        student.setWorkExperience(resumeBody.getWorkExperience());
        student.setIntensionGroup(resumeBody.getIntensionGroup());
        student.setJoinReason(resumeBody.getJoinReason());
        student.setAdvantage(resumeBody.getAdvantage());
        student.setDisadvantage(resumeBody.getDisadvantage());
        student.setFuturePlanning(resumeBody.getFuturePlanning());
        student.setSchoolAward(resumeBody.getSchoolAward());
        student.setDisposableTime(resumeBody.getDisposableTime());
        student.setSelfIntroduction(resumeBody.getSelfIntroduction());
        student.setResumeId(resumeBody.getResumeId());
        student.setAdjustment(resumeBody.getAdjustment());
        student.setPath(resumeBody.getPath());
        student.setStudentId(resumeBody.getStudentId());
        student.setFileName(resumeBody.getFileName());
        student.setResumeState(resumeBody.getResumeState());
        student.setSMSState(resumeBody.getSMSState());
        return student;
    }
}
