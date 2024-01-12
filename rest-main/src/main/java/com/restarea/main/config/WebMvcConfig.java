package com.restarea.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/asset/*")
                .addResourceLocations("classpath:/static/asset/");
        registry.addResourceHandler("/dist/**")
                .addResourceLocations("classpath:/static/dist/");
    }
}
