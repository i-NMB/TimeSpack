package com.bilicute.spacetime.config;

import com.bilicute.spacetime.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author i囡漫笔
 * @description 配置类（注册拦截器等）
 * @date 2024/4/18
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private LoginInterceptors loginInterceptors;
    @Autowired
    public void setLoginInterceptors(LoginInterceptors loginInterceptors) {
        this.loginInterceptors = loginInterceptors;
    }

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
                        "/**/article/listWeighting",
                        "/**/category/detail",
                        "/**/comment/num",
                        "/comment/get",
                        "/**/article/detail")
                .excludePathPatterns("/error");

    }
}
