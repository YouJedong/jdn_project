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
                .addPathPatterns("/api/**"); // 전체 경로에 적용
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/aps/**").allowedOrigins("http://localhost:3000", "https://jdnights-dev.com");
    }
}