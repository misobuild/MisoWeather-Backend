package com.misobuild.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;

/**
 * url/swagger-ui/
 *
 * @author yeon
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    String apiTitle = "MisoWeather REST API Documnet";
    String apiDescription = "@Author tmddusgood | Yeon";
    String name = "Seungyeon Kang";
    String github = "https://github.com/tmddusgood";
    String email = "tmddusgood@gmail.com";

    String referenceName = "Server Token";
    String keyName = "serverToken";
    String header = "header";
    String version = "1.0.0";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(AuthenticationPrincipal.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                .securityContexts(securityContext())
                .securitySchemes(singletonList(apiKey()));
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(apiTitle)
                .description(apiDescription)
                .version(version)
                .contact(new Contact(name, github, email))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(referenceName, keyName, header);
    }

    private List<SecurityReference> securityReference = singletonList(SecurityReference.builder()
            .reference(referenceName)
            .scopes(new AuthorizationScope[0])
            .build()
    );

    private List<SecurityContext> securityContext() {
        List<SecurityContext> lsc = new ArrayList<>();

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/member"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.DELETE))
                .build()
        );

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/member"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.GET))
                .build()
        );

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/comment"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.POST))
                .build()
        );

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/survey"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.POST))
                .build()
        );

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/survey/member"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.GET))
                .build()
        );

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/survey/precheck"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.GET))
                .build()
        );

        lsc.add(SecurityContext.builder()
                .securityReferences(securityReference)
                .forPaths(PathSelectors.ant("/api/member-region-mapping/default"))
                .forHttpMethods(Predicate.isEqual(HttpMethod.PUT))
                .build()
        );

        return lsc;
    }
}