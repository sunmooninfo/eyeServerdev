package com.eye.common.config;

import com.eye.common.annotation.support.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CommonWebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private CommonConfigPathInterceptor commonConfigPathInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonConfigPathInterceptor).addPathPatterns("/wx/topic/detail");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
