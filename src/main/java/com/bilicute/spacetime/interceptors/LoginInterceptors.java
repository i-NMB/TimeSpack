package com.bilicute.spacetime.interceptors;


import com.bilicute.spacetime.utils.JwtUtil;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * @author i囡漫笔
 * @description 每个页面打开时的拦截器-登陆
 * @date 2024/4/18
 */

@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * @param request: 
     * @param response: 
     * @param handler:
     * @return boolean
     * @author i囡漫笔
     * @description 登陆令牌认证;
     * @date 2024/4/18
     * ----------
     * preHandle详细信息
     * 调用时间：Controller方法处理之前
     * 执行顺序：链式Interceptor情况下，Interceptor按照声明的顺序一个接一个执行
     * 若返回false，则中断执行，注意：不会进入afterCompletion
     * ----------
     */
    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //令牌认证
//        String token = request.getHeader("Authorization");
        String token = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token =  cookie.getValue();
                }
            }
        }


        try {
            //从redis中获取token
//            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
//            String redisToken = operations.get(token);
//            if (redisToken == null) {
//                //redisToken失效
//                throw new RuntimeException();
//            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);//把业务数据放入ThreadLocal中
            return true;//放行
        } catch (Exception e) {
            //http响应状态码为401
            response.setStatus(401);
            return false;
        }
    }

    /** 
    * @description 清空TL数据
     *
     * ----------
     * 调用前提：preHandle返回true
     * 调用时间：DispatcherServlet进行视图的渲染之后
     * 多用于清理资源
     * ----------
    */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //清空数据
        ThreadLocalUtil.remove();
    }
}
