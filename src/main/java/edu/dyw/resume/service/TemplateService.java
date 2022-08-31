package edu.dyw.resume.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.dyw.resume.entity.Template;
import edu.dyw.resume.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    public Map<String,Object> saveTemplate(String jsonString){
        HashMap<String, Object> result = new HashMap<>();
        try {
            JSONObject jsonObject = JSON.parseObject(jsonString);
            String type = jsonObject.getString("type");
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            Template template = new Template();
            template.setType(type).setTitle(title).setContent(content);
            templateMapper.insert(template);
            result.put("state",true);
            result.put("msg","保存短信模板成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("state",false);
            result.put("msg","保存短信模板失败");
            return result;
        }

    }

    public Map<String,Object> getTemplates(){
        HashMap<String, Object> result = new HashMap<>();
        try {
            QueryWrapper<Template> queryWrapper = new QueryWrapper<>();
            queryWrapper.ge("id",0);
            List<Template> templates = templateMapper.selectList(queryWrapper);
            ArrayList<Template> screenPassed = new ArrayList<>();
            ArrayList<Template> interviewSuccess = new ArrayList<>();
            ArrayList<Template> interviewFailed = new ArrayList<>();
            for (Template template:templates){
                if (template.getType().equalsIgnoreCase("0")){
                    screenPassed.add(template);
                }else if (template.getType().equalsIgnoreCase("1")){
                    interviewSuccess.add(template);
                }else if (template.getType().equalsIgnoreCase("2")){
                    interviewFailed.add(template);
                }
            }
            result.put("state",true);
            result.put("msg","获取短信模板成功");
            HashMap<String, Object> data = new HashMap<>();
            data.put("screenPassed",screenPassed);
            data.put("interviewSuccess",interviewSuccess);
            data.put("interviewFailed",interviewFailed);
            result.put("data",data);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("state",false);
            result.put("msg","获取短信模板失败");
            return result;
        }
    }

    public Map<String,Object> deleteTemplate(Integer id){
        HashMap<String, Object> result = new HashMap<>();
        try {
            templateMapper.deleteById(id);
            result.put("state",true);
            result.put("msg","删除短信模板成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("state",false);
            result.put("msg","删除短信模板失败");
            return result;
        }
    }
}
