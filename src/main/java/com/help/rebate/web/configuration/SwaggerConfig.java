package com.help.rebate.web.configuration;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.Paths;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 表示开启生成接口文档功能（只有开启了openAPi，才可以实现生成接口文档的功能）
 *
 * @author zfcisbest
 * @date 21/11/14
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(aipInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo aipInfo() {
        return new ApiInfoBuilder()
                .title("长风破浪会有时-直挂云帆济沧海")
                .description("详细接口文档")
                .contact(new Contact("fachao.zfc", "http://tangpingbujuan.com", "zfcisbest@163.com"))
                .version("1.0.0")
                .build();
    }
}
