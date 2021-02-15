package com.yoo.lms.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebserviceConfig implements WebMvcConfigurer {


    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/longinForm")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/join/**")
                .addPathPatterns("/*/question/**")
                .addPathPatterns("/myPage/**");

    }
}
