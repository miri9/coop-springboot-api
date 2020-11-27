package com.study.springbootjpa.miri.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * [스웨거 설정]
 * https://www.javaguides.net/2018/10/spring-boot-2-restful-api-documentation-with-swagger2-tutorial.html - guide
 * https://swagger.io/specification/					- specification
 * https://springfox.github.io/springfox/docs/current/	- documentation
 * https://springboot.tistory.com/24	- 기본설정
 * https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api     - 튜토리얼 (Vaildation 추가하기)
 * 
 * [테스트 URL]
 * http://localhost:port/v2/api-docs
 * http://localhost:port/swagger-ui.html
 * 
 * 
 * [기타 애너테이션]
 * - @Configuration : 해당 클래스가 적어도 1개 이상의 @Bean 메서드를 가져야 한다. 또한 bean 정의 + 런타임 시점 해당 bean들의 service request 들을 생성하기 위해 Spring Container 에 의해 processed 될 수도 있다.
 * - @EnableSwagger2 : 위 애너테이션과 함께, 자바 컨피그 클래스 파일에 붙어야함.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // private ApiInfo apiInfo;
    // private Contact contact;
    
    /**
     * Docket 빈 설정
     */
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.study.springbootjpa.miri.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getInfo());
    }
    
    /**
     * Api Documentation 등 apiInfo 커스텀 설정
     * TODO : 적용안됨
     */
    public ApiInfo getInfo(){
        Contact contact = new Contact("ishift","http://www.ishift.co.kr/","mrpark@ishift.co.kr");
        return new ApiInfoBuilder()
                .title("Spring-Boot REST API")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0")
                .contact(contact)
                .description("Spring-Boot 기반 Rest Api 입니다.")
                .extensions(Collections.emptyList())
                .termsOfServiceUrl("Term of Service")
                .build();
        // new ApiInfoBuilder()
        //         .title("Spring-Boot REST API")
        //         .license("Apache 2.0")
        //         .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
        //         .version("1.0")
        //         .contact(contact)
        //         .description("Spring-Boot 기반 Rest Api 입니다.")
        //         .extensions(Collections.emptyList())
        //         .termsOfServiceUrl("Term of Service")
        //         .build();
    }

    /**
     * UI 설정
     */

    
}
