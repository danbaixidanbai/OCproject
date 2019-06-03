package com.ouxuxi.configuration;

import com.ouxuxi.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/cms/**");
        loginRegistry.excludePathPatterns("/login");
        loginRegistry.excludePathPatterns("/register");
        loginRegistry.excludePathPatterns("/loginout");
        loginRegistry.excludePathPatterns("/index/**");
        loginRegistry.excludePathPatterns("/list");
        loginRegistry.excludePathPatterns("/kaptcha");
        loginRegistry.excludePathPatterns("/courseclassify/**");
        loginRegistry.excludePathPatterns("/course/**");
        loginRegistry.excludePathPatterns("/new/**");
        loginRegistry.excludePathPatterns("/user/getlogin");
        loginRegistry.excludePathPatterns("/user/getuserbyid");
        loginRegistry.excludePathPatterns("/user/getregister");
        loginRegistry.excludePathPatterns("/user/getuser");
        loginRegistry.excludePathPatterns("/coursesession/**");
        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/**");
        loginRegistry.excludePathPatterns("/js/**");
        loginRegistry.excludePathPatterns("/image/**");
    }

}
