package org.example.stepwave.config;

import org.example.stepwave.web.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // /api/users/{id}: вход обязателен, дальше контроллер сверяет id с сессией.
        // POST /api/users остаётся открытым, это регистрация.
        registry.addInterceptor(new AuthInterceptor(true))
                .addPathPatterns("/api/users/*");

        registry.addInterceptor(new AuthInterceptor(false))
                .addPathPatterns("/dashboard", "/profile");
    }
}
