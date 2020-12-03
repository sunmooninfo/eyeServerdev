package com.eye.brand.config;

import com.eye.brand.annotation.support.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class BrandMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private ConfigPathInterceptor configPathInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(configPathInterceptor).addPathPatterns("/langbo/brand/detail");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
