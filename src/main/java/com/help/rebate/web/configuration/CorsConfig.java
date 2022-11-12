package com.help.rebate.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfig  extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("====Access-Control-Allow-Origin  REGISTRY====");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("====swagger-ui.html handler====");
        /**
         * SpringBoot自动配置本身并不会把/swagger-ui.html
         * 这个路径映射到对应的目录META-INF/resources/下面
         * 采用WebMvcConfigurerAdapter将swagger的静态文件进行发布;
         */
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        super.addResourceHandlers(registry);
    }
}