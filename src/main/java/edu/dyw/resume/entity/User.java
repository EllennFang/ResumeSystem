package edu.dyw.resume.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("User")
public class User implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)                  //id
    private Long id;

    @TableField("name")             //姓名
    private String name;

    @TableField("sex")              //性别
    private String sex;

    @TableField("phone")            //联系电话
    private String phone;

    @TableField("grade")            //年级
    private String grade;

    @TableField("department")       //学院全称
    private String department;

    @TableField("major")            //专业
    private String major;

    @TableField("QQ")               //QQ
    private String QQ;

    @TableField("mailaddress")      //邮箱地址
    private String mailaddress;

    @TableField("address")          //宿舍号
    private String address;

    @TableField("workExperience")       //工作经历，，，因为考虑到数据库大小写问题，所以不改名workExperience
    private String workExperience;

    @TableField("intensionGroup")   //意向组别,,intensionGroup
    private String intensionGroup;

    @TableField("joinReason")           //报名理由，，，因为考虑到数据库大小写问题，所以不改名joinReason
    private String joinReason;

    @TableField("advantage")        //自身优势
    private String advantage;

    @TableField("futurePlanning")             //未来计划，，，因为考虑到数据库大小写问题，所以不改名futurePlanning
    private String futurePlanning;

    @TableField("schoolAward")            //在校奖励，schoolAward
    private String schoolAward;

    @TableField("disposableTime")        //可支配时间，，，disposableTime
    private String disposableTime;

    @TableField("selfIntroduction")     //自我介绍，selfIntroduction
    private String selfIntroduction;

    @TableField("path")             //简历文件路径
    private String path;

    @TableField("adjustment")       //是否接受调剂
    private String adjustment;

    @TableField("disadvantage")     //自身劣势
    private String disadvantage;

    @TableField("resumeId")         //凭证码
    private String resumeId;

    @TableField("studentId")         //学号
    private String studentId;

    @TableField("fileName")         //用户上传的文件名
    private String fileName;

    @TableField("resumeState")       //简历的状态
    private String resumeState;

    @TableField("SMSState")         //短信发送状态
    private String SMSState;

    @TableField("comment")          //管理员对简历的评价
    private String comment;

    @TableLogic
    private Boolean deleted;
}
