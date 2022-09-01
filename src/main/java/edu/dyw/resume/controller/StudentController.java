package edu.dyw.resume.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.dyw.resume.entity.AjaxResult;
import edu.dyw.resume.entity.Extension;
import edu.dyw.resume.entity.ResumeBody;
import edu.dyw.resume.entity.User;
import edu.dyw.resume.mapper.ExtensionMapper;
import edu.dyw.resume.mapper.UserMapper;
import edu.dyw.resume.service.SysResumeService;
import edu.dyw.resume.utils.VerifyCodeUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import edu.dyw.resume.utils.ConcurrentHashMapCacheUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/stu")
@CrossOrigin //允许跨域
public class StudentController {

    @Value("${filePath}")
    private String filePath;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysResumeService sysResumeService;

    @Autowired
    private ExtensionMapper extensionMapper;


    /**
     *
     * @param codeKey 前端保存在Sesssion Storage中验证码的key
     * @return
     */
    @GetMapping("/getCode")
    public Map<String,Object> getVerifyCode(String codeKey){
        HashMap<String, Object> map = new HashMap<>();
        try {
            //随机生成一个4位的验证码
            String code = VerifyCodeUtils.generateVerifyCode(4);

            //后台测试接口时用，在控制台打印出正确验证码，减少测试时看验证码的无用功，直接在postman中输入就行
            System.out.println("正确的验证码是："+code);

            //用4位验证码生成base64编码后的图片字符串(前端可直接展示)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            VerifyCodeUtils.outputImage(80,40,byteArrayOutputStream,code);
            String string = "data:image/png;base64,"+Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
            //根据codeKey保存验证码值到本地内存中(5个小时的有效时间)
            if(codeKey == null || codeKey.length()==0){
                codeKey = UUID.randomUUID().toString();
            }
            ConcurrentHashMapCacheUtils.setCache(codeKey,code,18000L);
            //返回map
            map.put("state",true);
            map.put("msg","获取验证码成功");
            map.put("codeKey",codeKey);
            map.put("imgString",string);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","获取验证码失败");
        }
        return map;
    }



    /**
     * 简历填写方法
     * @param resumeBody 注册信息
     * @return 结果
     */
    @PostMapping("/resume")
    public Map<String,Object> save(ResumeBody resumeBody, @RequestParam("projectfile") MultipartFile file) throws IOException
    {
        Map<String, Object> map = new HashMap<>();

        //检验是否到截止时间
        Extension extension = extensionMapper.selectById(1);
        Date deadline = extension.getDeadline();
        Date now = new Date();
        if (now.after(deadline)){
            map.put("state",false);
            map.put("msg","填报简历的截止时间已到");
            return map;
        }
        //检验验证码
        Map<String, Object> codeMap = checkCode(resumeBody);
        if (!(Boolean) codeMap.get("state")) {
            return codeMap;
        }


        System.out.println("处理前"+resumeBody);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String resumeId = resumeBody.getResumeId();
        queryWrapper.eq("resumeId",resumeId);

        //根据resumeId获取之前保存的简历对象
        User userDB = userMapper.selectOne(queryWrapper);

        if(resumeId!=null&&!resumeId.isEmpty()&&userDB !=null){//简历之前已经存在,则替换原有的简历信息
            System.out.println("修改简历：resumeId="+resumeId);
            //修改文件信息
            if(!file.isEmpty()){
                //删除原来保存在服务器文件
                String path = userDB.getPath();
                File oldFile = new File(path);
                oldFile.delete();
                //保存新文件
                Map<String, Object> fileMap = savaFile(file);
                if (!(Boolean) fileMap.get("state")){
                    return fileMap;
                }
                resumeBody.setFileName(file.getOriginalFilename());
                resumeBody.setPath((String) fileMap.get("path"));
                System.out.println("文件不为空");
            }else {
                resumeBody.setFileName(userDB.getFileName());
                resumeBody.setPath(userDB.getPath());
                System.out.println("文件为空");
            }

            //修改旧简历
            resumeBody.setResumeState(userDB.getResumeState()).setSMSState(userDB.getSMSState());
            AjaxResult result = sysResumeService.updateById(resumeBody,userDB.getId());

            map.put("state",result.isState());
            map.put("msg",result.getMsg());
        }else{
            //保存新的简历
            map = saveResume(resumeBody,file);
        }

        return map;
    }


    private Map<String,Object> savaFile(MultipartFile file) {

        Map<String, Object> map = new HashMap<>();

        //附件上传
        File fileDir = new File(filePath);//保存的文件夹路径
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        if (!file.isEmpty()) {
            String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            try {
                file.transferTo(new File(filePath + newFileName));
            } catch (IOException e) {
                e.printStackTrace();
                map.put("state", false);
                map.put("msg", "文件上传失败");
                return map;
            }
            map.put("state",true);
            map.put("msg", "文件保存成功");
            map.put("path",filePath+newFileName);
            return map;
        }
        map.put("path","");
        map.put("msg", "文件为空");
        map.put("state",true);
        return map;
    }


    //检查验证码
    private Map<String,Object> checkCode(ResumeBody resumeBody){

        Map<String, Object> map = new HashMap<>();

        System.out.println("codekey是"+resumeBody.getCodeKey());

        String captcha = (String) ConcurrentHashMapCacheUtils.getCache(resumeBody.getCodeKey());
        if (captcha == null)
        {
            map.put("state",false);
            map.put("msg","验证码已失效");
            System.out.println("验证码已失效");
            return map;
        }
        if (!resumeBody.getCodeKeyInput().equalsIgnoreCase(captcha))
        {
            map.put("state",false);
            map.put("msg","验证码错误");
            System.out.println("验证码错误");
            return map;
        }
        map.put("state",true);
        return map;
    }


    /**
     *  保存简历信息
     * @param resumeBody
     * @param file
     * @return
     */
    private Map<String,Object> saveResume(ResumeBody resumeBody,MultipartFile file){

        HashMap<String, Object> map = new HashMap<>();

        //附件上传
        File fileDir = new File(filePath);//保存的文件夹路径
        if (!fileDir.exists()){
            fileDir.mkdir();
        }
        if (!file.isEmpty()) {
            String newFileName = UUID.randomUUID().toString()+"."+ FilenameUtils.getExtension(file.getOriginalFilename());
            try {
                file.transferTo(new File(filePath+newFileName));
            } catch (IOException e) {
                e.printStackTrace();
                map.put("state",false);
                map.put("msg","文件上传失败");
                return map;
            }
            //添加path,fileName信息到对象
            resumeBody.setPath(filePath+newFileName);
            resumeBody.setFileName(file.getOriginalFilename());
        }

        String resumeId = resumeBody.getResumeId();


        //生成随机数作为凭证码,并把凭证码保存在数据库中
        resumeId = UUID.randomUUID().toString();
        resumeBody.setResumeId(resumeId);


        //验证码和注册
        System.out.println("实验打印"+resumeBody.getCodeKeyInput());
        // 生成返回信息
        System.out.println("处理后"+resumeBody);
        //设置新简历的状态，短信发送状态
        resumeBody.setResumeState("0");
        resumeBody.setSMSState("0");
        AjaxResult result = sysResumeService.save(resumeBody);
        map.put("state",result.isState());
        map.put("msg",result.getMsg());
        if(result.isState()){
            map.put("resumeId",resumeId);
        }

        return map;
    }

    /**
     * 通过凭证码查询简历信息方法
     * @param resumeId 凭证码  resumeid
     * @return user
     */
    @GetMapping("/checkByResumeId")
    public Map<String,Object> checkByResumeId(@RequestParam("resumeId") String resumeId,
                                              @RequestParam("codeKey") String codeKey,@RequestParam("codeKeyInput") String codeKeyInput)
    {

        //检查验证码
        Map<String, Object> codeMap = checkCode(new ResumeBody().setCodeKey(codeKey).setCodeKeyInput(codeKeyInput));
        if (!(boolean)codeMap.get("state")){
            return codeMap;
        }

        HashMap<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //查询凭证码为 resumeId 的一条记录
            queryWrapper.eq("resumeId",resumeId);
            User user = userMapper.selectOne(queryWrapper);
            //设置评语不可见
            user.setComment("");
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("data",user);
        }catch (Exception e) {
            map.put("state", false);
            map.put("msg", "查询失败");
        }
        return map;
    }


    /**
     * 通过姓名+学号查询简历信息方法
     * @param studentId 学号  name 姓名
     * @return user
     */
    @GetMapping("/checkByStudentId")
    public Map<String,Object> checkByStudentId(@RequestParam("name") String name,@RequestParam("studentId") String studentId
            ,@RequestParam("codeKey") String codeKey,@RequestParam("codeKeyInput") String codeKeyInput)
    {
        //检查验证码
        Map<String, Object> codeMap = checkCode(new ResumeBody().setCodeKey(codeKey).setCodeKeyInput(codeKeyInput));
        if (!(boolean)codeMap.get("state")){
            return codeMap;
        }

        HashMap<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            //根据条件查询查询
            Map<String, String> queryMap = new HashMap<>();
            queryMap.put("name",name);
            queryMap.put("studentId",studentId);
            queryWrapper.allEq(queryMap);
            User user = userMapper.selectOne(queryWrapper);
            if(user==null){
                map.put("state",false);
                map.put("msg","查询失败");
                return map;
            }
            //设置评语不可见
            user.setComment("");
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("data",user);
        }catch (Exception e) {
            map.put("state", false);
            map.put("msg", "查询失败");
        }
        return map;
    }





    /**
     * 通过凭证码下载简历附件方法
     * @param resumeId 凭证码
     * @return 结果
     */
    @GetMapping("/download")
    public String download(@RequestParam("resumeId") String resumeId ,HttpServletResponse response)
    {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //查询凭证码为 resumeId 的一条记录
        queryWrapper.eq("resumeId",resumeId);
        User user = userMapper.selectOne(queryWrapper);
        if(user!=null&&user.getPath()!=null){
            InputStream bis = null;
            OutputStream outputStream = null;
            try {
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(user.getFileName(),"utf-8"));
                String path = user.getPath();
                File file = new File(path);
                bis = new BufferedInputStream(new FileInputStream(file));
                outputStream = response.getOutputStream();
                byte[] bytes = new byte[1024];
                int read = bis.read(bytes);
                while (read>0){
                    outputStream.write(bytes,0,read);
                    read = bis.read(bytes);
                }
                outputStream.flush();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (bis!=null){
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream!=null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        map.put("state",false);
        map.put("msg","下载失败");
        return JSONUtils.toJSONString(map);
    }
}
