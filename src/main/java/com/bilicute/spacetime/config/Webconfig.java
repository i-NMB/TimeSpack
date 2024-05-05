package com.bilicute.spacetime.config;

import com.bilicute.spacetime.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author i囡漫笔
 * @description 配置类（注册拦截器等）
 * @date 2024/4/18
 */

@Configuration
public class Webconfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

    /**
     * @param registry:  [registry]
     * @author i囡漫笔
     * @description 拦截未登录请求
     * @date 2024/4/18
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登陆接口和注册接口不拦截
        registry.addInterceptor(loginInterceptors)
                .excludePathPatterns("/**/user/login",
                        "/**/user/register",
                        "/**/user/getUser",
                        "/**/getCode/img",
                        "/**/getCode/mail",
                        "/**/getCode/phone",
                        "/**/user/giveToken",
                        "/**/article",
                        "/**/category/detail",
                        "/**/comment/num",
                        "/comment/get",
                        "/**/article/detail")
                .excludePathPatterns("/error");

    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//        //允许跨域访问资源定义
//        registry.addMapping("/**")
//                //(只允许本地的指定端口访问)允许所有
//                .allowedOrigins("http://127.0.0.1:8848")
//                // 允许发送凭证: 前端如果配置改属性为true之后，则必须同步配置
//                .allowCredentials(true)
//                // 允许所有方法
//                .allowedMethods("*")
//
//                .allowedHeaders("*");
//    }


}
