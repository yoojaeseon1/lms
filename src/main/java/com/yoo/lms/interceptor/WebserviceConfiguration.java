package com.yoo.lms.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebserviceConfiguration implements WebMvcConfigurer {


    private final MemberInterceptor memberInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/courses/**")
                .addPathPatterns("/inquiryBoard/**")
                .addPathPatterns("/myPage/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");

    }
}
