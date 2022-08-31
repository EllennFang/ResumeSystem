package edu.dyw.resume.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.dyw.resume.entity.*;
import edu.dyw.resume.mapper.AdminMapper;
import edu.dyw.resume.mapper.ExtensionMapper;
import edu.dyw.resume.mapper.LogMapper;
import edu.dyw.resume.mapper.UserMapper;
import edu.dyw.resume.service.AdminService;
import edu.dyw.resume.service.SysResumeService;
import edu.dyw.resume.service.TemplateService;
import edu.dyw.resume.utils.ConcurrentHashMapCacheUtils;
import edu.dyw.resume.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private ExtensionMapper extensionMapper;

    @Autowired
    private SysResumeService resumeService;

    @Autowired
    private TemplateService templateService;


    //管理员登录
    @PostMapping("/login")
    public Map<String,Object> login(Admin admin,String codeKey,String codeKeyInput){
        Map<String, Object> codeMap = checkCode(codeKey, codeKeyInput);
        if (!(Boolean) codeMap.get("state")){
            return codeMap;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            Map<String,Object> loginMap = adminService.login(admin);
            if ((boolean)loginMap.get("state")){
                HashMap<String, String> payload = new HashMap<>();
                payload.put("adminId",""+loginMap.get("adminId"));
                payload.put("username",admin.getUsername());
                payload.put("role",(String) loginMap.get("role"));
                //生成jwt令牌
                String token = JWTUtils.getToken(payload);
                //生成日志信息
                Log log = new Log();
                log.setUsername(admin.getUsername());
                log.setTime(new Date());
                log.setInfo("管理员["+admin.getUsername()+"]登录成功");
                logMapper.insert(log);

                map.put("state",true);
                map.put("msg","登录成功");
                map.put("token",token);
                map.put("adminId",loginMap.get("adminId"));
                map.put("role",loginMap.get("role"));
                return map;
            }
            map.put("state",false);
            map.put("msg",loginMap.get("msg"));
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","登录失败");
        }
        return map;
    }

    //添加管理员账号
    @PostMapping("/addAdmin")
    public Map<String,Object> addAdmin(Admin admin, HttpServletRequest request){

        HashMap<String, Object> map = new HashMap<>();
        //根据token负载中的管理员信息判断身份
        String token = request.getHeader("token");
        DecodedJWT decode = JWT.decode(token);
        String role = decode.getClaim("role").asString();
        if (role.equalsIgnoreCase("root")){
            //身份验证成功，执行添加操作
            Map<String, Object> addMap = adminService.addAdmin(admin.getUsername(), admin.getPassword());
            return addMap;
        }
        //身份验证失败
        map.put("state",false);
        map.put("msg","权限不足");
        return map;
    }

    //根据姓名查询简历
    @GetMapping("/queryByName")
    public Map<String,Object> queryByName(String name,@RequestParam(name = "page",required = false) Integer page){
        //默认为第一页
        if (page==null){
            page=1;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (name!=null){
            Page<User> userPage = new Page<>(page,10);
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("name",name);
            IPage<User> result = userMapper.selectPage(userPage, userQueryWrapper);
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("page",result.getCurrent());
            map.put("total",result.getTotal());
            map.put("size",result.getSize());
            map.put("pages",result.getPages());
            map.put("data",result.getRecords());
            return map;
        }
        map.put("state",false);
        map.put("msg","请输入姓名");
        return map;
    }

    //根据学号查询简历
    @GetMapping("/queryByStuId")
    public Map<String,Object> queryByStuId(String stuId,@RequestParam(name = "page",required = false) Integer page){
        //默认查询第一页
        if (page==null){
            page=1;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (stuId!=null){
            Page<User> userPage = new Page<>(page,10);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("studentId",stuId);
            Page<User> result = userMapper.selectPage(userPage, queryWrapper);
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("page",result.getCurrent());
            map.put("total",result.getTotal());
            map.put("pages",result.getPages());
            map.put("size",result.getSize());
            map.put("data",result.getRecords());
            return map;
        }
        map.put("state",false);
        map.put("msg","请输入学号");
        return map;
    }

    //根据意向组别查询简历
    @GetMapping("/queryByGroup")
    public Map<String,Object> queryByGroup(String intensionGroup,@RequestParam(name = "page",required = false) Integer page){
        if (page==null){
            page=1;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (intensionGroup!=null){
            Page<User> userPage = new Page<>(page,10);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            String[] groups = intensionGroup.split(",");
            for(String group:groups){
                queryWrapper.like("intensionGroup",group);
            }
            queryWrapper.orderByAsc("resumeState");
            Page<User> result = userMapper.selectPage(userPage, queryWrapper);
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("page",result.getCurrent());
            map.put("total",result.getTotal());
            map.put("pages",result.getPages());
            map.put("size",result.getSize());
            map.put("data",result.getRecords());
            return map;
        }
        map.put("state",false);
        map.put("msg","请选择意向组别");
        return map;
    }

    //根据简历状态查询
    @GetMapping("/queryByResumeState")
    public Map<String,Object> queryByResumeState(String resumeState,@RequestParam(name = "page",required = false) Integer page){
        //默认查询第一页数据
        if (page==null){
            page=1;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (resumeState!=null){
            Page<User> userPage = new Page<>(page, 10);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("resumeState",resumeState);
            Page<User> result = userMapper.selectPage(userPage, queryWrapper);
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("page",result.getCurrent());
            map.put("total",result.getTotal());
            map.put("pages",result.getPages());
            map.put("size",result.getSize());
            map.put("data",result.getRecords());
            return map;
        }
        map.put("state",false);
        map.put("msg","请选择简历的状态");
        return map;
    }

    //根据短信发送状态查询
    @GetMapping("/queryBySMSState")
    public Map<String,Object> queryBySMSState(String SMSState,@RequestParam(name = "page",required = false) Integer page){
        //默认查询第一页数据
        if (page==null){
            page=1;
        }
        HashMap<String, Object> map = new HashMap<>();
        if (SMSState!=null){
            Page<User> userPage = new Page<>(page, 10);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("SMSState",SMSState);
            Page<User> result = userMapper.selectPage(userPage, queryWrapper);
            map.put("state",true);
            map.put("msg","查询成功");
            map.put("page",result.getCurrent());
            map.put("total",result.getTotal());
            map.put("pages",result.getPages());
            map.put("size",result.getSize());
            map.put("data",result.getRecords());
            return map;
        }
        map.put("state",false);
        map.put("msg","请选择简历的状态");
        return map;
    }

    //管理员修改简历的状态
    @PutMapping("/updateResumeState")
    public Map<String,Object> updateResumeState(String resumeId,String resumeState,HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        if (resumeId==null){
            map.put("state",false);
            map.put("msg","请选择要修改的状态");
            return map;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resumeId",resumeId);
        User user = userMapper.selectOne(queryWrapper);
        if (user!=null){
            user.setResumeState(resumeState);
            userMapper.updateById(user);
            //添加日志信息
            Log log = new Log();
            DecodedJWT decodedJWT = JWT.decode(request.getHeader("token"));
            String username = decodedJWT.getClaim("username").asString();
            log.setUsername(username);
            log.setTime(new Date());
            log.setInfo("管理员["+username+"]修改了["+user.getName()+"]的简历状态");
            logMapper.insert(log);

            map.put("state",true);
            map.put("msg","修改成功");
            return map;
        }
        map.put("state",false);
        map.put("msg","修改失败");
        return map;
    }

    //修改短信发送状态
    @PutMapping("/updateSMSState")
    public Map<String,Object> updateSMSState(String resumeId, String SMSState,HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("resumeId",resumeId);
            User user = userMapper.selectOne(queryWrapper);
            user.setSMSState(SMSState);
            userMapper.updateById(user);

            //添加日志信息
            Log log = new Log();
            DecodedJWT decodedJWT = JWT.decode(request.getHeader("token"));
            String username = decodedJWT.getClaim("username").asString();
            log.setUsername(username);
            log.setTime(new Date());
            log.setInfo("管理员["+username+"]修改了["+user.getName()+"]的短信发送状态");
            logMapper.insert(log);

            map.put("state", true);
            map.put("msg","修改成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","修改失败");
            return map;
        }
    }

    //保存管理员对简历的评语
    @PostMapping("/saveComment")
    public Map<String,Object> saveComment(String resumeId,String comment){
        HashMap<String, Object> map = new HashMap<>();
        try {
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("resumeId", resumeId);
            User user = userMapper.selectOne(queryWrapper);
            user.setComment(comment);
            userMapper.updateById(user);
            map.put("state",true);
            map.put("msg","保存成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","保存失败");
            return map;
        }
    }

    //获取日志信息
    @GetMapping("/getLog")
    public Map<String,Object> getLog(@RequestParam(name = "page",required = false) Integer page){
        HashMap<String, Object> map = new HashMap<>();
        //默认查询第一页
        if (page==null){
            page=1;
        }
        try {
            Page<Log> logPage = new Page<>(page,20);
            QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("time");
            Page<Log> result = logMapper.selectPage(logPage, queryWrapper);
            map.put("state",true);
            map.put("page",result.getCurrent());
            map.put("size",result.getSize());
            map.put("pages",result.getPages());
            map.put("total",result.getTotal());
            map.put("logs",result.getRecords());
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","获取信息失败");
            return map;
        }

    }

    //获取简历填报截止时间
    @GetMapping("/getDeadline")
    public Map<String,Object> getDeadline(){
        HashMap<String, Object> map = new HashMap<>();
        try {
            Extension extension = extensionMapper.selectById(1);
            map.put("state",true);
            map.put("deadline",extension.getDeadline());
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","查询截止时间失败");
            return map;
        }
    }

    //设置简历填报的截止时间
    @PutMapping("/setDeadline")
    public Map<String,Object> setDeadline(Extension extension){
        HashMap<String, Object> map = new HashMap<>();
        try {
            extension.setId((long) 1);
            extensionMapper.updateById(extension);
            map.put("state",true);
            map.put("msg","修改成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","修改失败");
            return map;
        }
    }

    //超级管理员查看所有管理员的信息
    @GetMapping("/getAdmins")
    public Map<String,Object> getAdmins(HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        String token = request.getHeader("token");
        String role = JWTUtils.verifyToken(token).getClaim("role").asString();
        if (!role.equalsIgnoreCase("root")){
            hashMap.put("state",false);
            hashMap.put("msg","没有权限");
            return hashMap;
        }
        Map<String, Object> result = adminService.getAdmins();
        return result;
    }

    //超级管理员删除普通管理员账号
    @DeleteMapping("/deleteAdmin")
    public Map<String,Object> deleteAdmin(Integer id,HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        String token = request.getHeader("token");
        String role = JWTUtils.verifyToken(token).getClaim("role").asString();
        if (!role.equalsIgnoreCase("root")){
            hashMap.put("state",false);
            hashMap.put("msg","没有权限");
            return hashMap;
        }
        Map<String, Object> result = adminService.deleteAdmin(id);
        return result;
    }

    //超级管理员修改管理员信息
    @PutMapping("/updateAdmin")
    public Map<String,Object> updateAdmin(Admin admin,HttpServletRequest request){
        HashMap<String, Object> hashMap = new HashMap<>();
        String token = request.getHeader("token");
        String role = JWTUtils.verifyToken(token).getClaim("role").asString();
        if (!role.equalsIgnoreCase("root")){
            hashMap.put("state",false);
            hashMap.put("msg","没有权限");
            return hashMap;
        }
        Map<String, Object> result = adminService.updateAdmin(admin);
        return result;
    }

    //管理员删除某个简历
    @DeleteMapping("/deleteResume")
    public Map<String,Object> deleteResume(Long id){
        Map<String, Object> result = resumeService.deleteResume(id);
        return result;
    }

    //保存短信模板
    @PostMapping("/saveTemplate")
    public Map<String,Object> saveTemplate(@RequestBody String jsonString){
        Map<String, Object> result = templateService.saveTemplate(jsonString);
        return result;
    }

    //获取所有短信模板
    @GetMapping("/getTemplates")
    public Map<String,Object> getTemplates(){
        Map<String, Object> result = templateService.getTemplates();
        return result;
    }

    //删除某个短信模板
    @DeleteMapping("/deleteTemplate")
    public Map<String,Object> deleteTemplate(Integer id){
        Map<String, Object> result = templateService.deleteTemplate(id);
        return result;
    }
    //检查验证码
    private Map<String,Object> checkCode(String codeKey,String codeKeyInput){

        Map<String, Object> map = new HashMap<>();

        System.out.println("codekey是"+codeKey);

        String captcha = (String) ConcurrentHashMapCacheUtils.getCache(codeKey);
        if (captcha == null)
        {
            map.put("state",false);
            map.put("msg","验证码已失效");
            System.out.println("验证码已失效");
            return map;
        }
        if (!codeKeyInput.equalsIgnoreCase(captcha))
        {
            map.put("state",false);
            map.put("msg","验证码错误");
            System.out.println("验证码错误");
            return map;
        }
        map.put("state",true);
        return map;
    }
}
