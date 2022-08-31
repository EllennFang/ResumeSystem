package edu.dyw.resume.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author miaomiao
 */
@Data
@Accessors(chain = true)
public class ResumeBody {


    private String name;                //姓名

    private String sex;                  //性别

    private String phone;                 //联系电话

    private String grade;                //年级

    private String department;           //学院全称

    private String major;                  //专业

    private String QQ;                   //QQ

    private String mailaddress;             //邮箱地址

    private String address;                //宿舍号

    private String intensionGroup;     //意向组别

    private String adjustment;          //是否接受调剂

    private String joinReason;          //报名原因

    private String advantage;          //自身优势

    private String disadvantage;          //自身劣势

    private String futurePlanning;      //未来规划

    private String workExperience;    //工作经历

    private String disposableTime;      //可支配时间

    private String schoolAward;         //在校奖励

    private String selfIntroduction;    //自我介绍

    private String codeKeyInput;        //用户输入的验证码

    private String codeKey;             //验证码带的参数

    private String resumeId;            //凭证码

    private String path;                //附件路径

    private String studentId;           //学号

    private String fileName;            //用户上传的文件的名字

    private String resumeState;         //简历的状态

    private String SMSState;            //短信发送状态

}
