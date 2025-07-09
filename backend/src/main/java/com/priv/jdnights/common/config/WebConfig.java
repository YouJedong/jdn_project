package com.priv.jdnights.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LangInterceptor langInterceptor;

    public WebConfig(LangInterceptor langInterceptor) {
        this.langInterceptor = langInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(langInterceptor)
                .addPathPatterns("/**"); // 전체 경로에 적용
    }


}