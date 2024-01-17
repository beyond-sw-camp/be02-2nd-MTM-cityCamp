package com.example.campingontop.config;

import com.example.campingontop.house.model.House;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Operation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("All")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket houseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("House")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.house"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("User")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.user"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket likesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Likes")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.likes"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket cartApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Cart")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.cart"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Project - campingOnTop")
                .description("Hanwha_SW CAMP 2기 - campingOnTop")
                .version("1.0.0")
                .build();
    }
}