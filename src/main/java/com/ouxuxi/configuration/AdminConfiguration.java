package com.ouxuxi.configuration;

import com.ouxuxi.interceptor.AdminInterceptor;
import com.ouxuxi.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        AdminInterceptor adminInterceptor = new AdminInterceptor();
        InterceptorRegistration loginRegistry = registry.addInterceptor(adminInterceptor);
        // 拦截路径
        loginRegistry.addPathPatterns("/cms/**");
        // 排除路径
        loginRegistry.excludePathPatterns("/");
        loginRegistry.excludePathPatterns("/kaptcha");
        loginRegistry.excludePathPatterns("/courseclassify/**");
        loginRegistry.excludePathPatterns("/cms/cmslogin");
        loginRegistry.excludePathPatterns("/cms/islogin");
        loginRegistry.excludePathPatterns("/cms/getcmslogin");
        loginRegistry.excludePathPatterns("/new/**");
        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/**");
        loginRegistry.excludePathPatterns("/js/**");
        loginRegistry.excludePathPatterns("/image/**");
    }
}
