package com.works.metrostation.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Challange API")
                .description("Description for Challange")
                .termsOfServiceUrl("https://www.linkedin.com/in/ayd%C4%B1n-uzun-a251b01a3/")
                .license("Java License")
                .licenseUrl("uzun.aydinn@gmail.com").version("1.0").build();
    }
}
