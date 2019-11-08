package com.longge.bigfile.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author ezha42
 * @date 2019-06-16
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger",name="enabled",havingValue ="true")
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {

        List<Parameter> pars = new ArrayList<Parameter>();
        pars.add(new ParameterBuilder().name("Authorization").description("Authorization token")
            .modelRef(new ModelRef("string")).parameterType("header")
            .required(false).build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nike.gcsc.bigfile.rest"))
                .paths(PathSelectors.any()).build().globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("bigfile-service Api")
                .description("bigfile-service Api")
                .version("1.0")
                .build();
    }
}