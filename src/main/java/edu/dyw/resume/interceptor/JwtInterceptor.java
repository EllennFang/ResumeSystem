package edu.dyw.resume.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import edu.dyw.resume.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String token = request.getHeader("token");
            JWTUtils.verifyToken(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            HashMap<String, Object> map = new HashMap<>();
            map.put("state",false);
            map.put("code",403);
            map.put("msg","没有权限");
            String string = JSONUtils.toJSONString(map);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(string);
            return false;
        }

    }
}
