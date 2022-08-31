package edu.dyw.resume.service;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.dyw.resume.entity.Admin;
import edu.dyw.resume.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.HashAttributeSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;


    public Map<String,Object> login(Admin admin){
        HashMap<String, Object> map = new HashMap<>();
        if (admin!=null){
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            HashMap<String, String> conditions = new HashMap<>();
            conditions.put("username",admin.getUsername());
            conditions.put("password",admin.getPassword());
            queryWrapper.allEq(conditions);
            Admin adminDB = adminMapper.selectOne(queryWrapper);
            if(adminDB!=null){
                map.put("state",true);
                map.put("msg","登录成功");
                map.put("adminId",adminDB.getId());
                map.put("role",adminDB.getRole());
                return map;
            }
        }
        map.put("state",false);
        map.put("msg","用户名或密码错误");
        return map;
    }

    public Map<String,Object> addAdmin(String username,String password){
        HashMap<String, Object> map = new HashMap<>();
        //username,password不能为空
        if (username==null ||username.equals("") || password==null || password.equals("")){
            map.put("state",false);
            map.put("msg","用户名和密码不能为空");
            return map;
        }
        //判断username是否重复
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        Admin adminDB = adminMapper.selectOne(queryWrapper);
        if (adminDB!=null){
            map.put("state",false);
            map.put("msg","用户名已存在");
            return map;
        }
        //保存信息到数据库
        try {
            Admin admin = new Admin();
            admin.setUsername(username).setPassword(password).setRole("admin");
            adminMapper.insert(admin);
            map.put("state",true);
            map.put("msg","添加成功");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("state",false);
            map.put("msg","添加失败");
            return map;
        }
    }

    public Map<String,Object> getAdmins(){
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            List<Admin> admins = adminMapper.getAll();
            hashMap.put("state",true);
            hashMap.put("data",admins);
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("state",false);
            hashMap.put("msg","查询失败");
            return hashMap;
        }
    }

    public Map<String,Object> deleteAdmin(Integer id){
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            if (id==1){
                hashMap.put("state",false);
                hashMap.put("msg","超级管理员账号不能删除");
                return hashMap;
            }
            adminMapper.deleteById(id);
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

    public Map<String,Object> updateAdmin(Admin admin){
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            admin.setRole("admin");
            if (StringUtils.isEmpty(admin.getUsername()) || StringUtils.isEmpty(admin.getPassword())){
                hashMap.put("state",false);
                hashMap.put("msg","用户名和密码不能为空");
                return hashMap;
            }
            adminMapper.updateById(admin);
            hashMap.put("state",true);
            hashMap.put("msg","修改成功");
            return hashMap;
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("state",false);
            hashMap.put("msg","修改失败");
            return hashMap;
        }
    }
}
