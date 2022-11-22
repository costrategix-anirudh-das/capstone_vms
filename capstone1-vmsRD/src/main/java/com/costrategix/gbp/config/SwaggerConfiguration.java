package com.costrategix.gbp.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import com.costrategix.gbp.constants.SwaggerConstant;
import com.google.common.collect.Lists;

import io.swagger.v3.oas.models.security.SecurityScheme;



@Configuration
public class SwaggerConfiguration {
	
//	@Bean
//	public Docket apiDocket() {
//		return new Docket(DocumentationType.SWAGGER_2);
//	}
//	
//	private ApiInfo apiInfo() {
//		return new ApiInfo(SwaggerConstant.API_TITLE, SwaggerConstant.API_DESCRIPTION, SwaggerConstant.API_VERSION, SwaggerConstant.TERM_OF_SERVICE, contact(), 
//				SwaggerConstant.LICENSE, SwaggerConstant.LICENSE_URL, Collections.emptyList());
//	}
//	
//	private Contact contact() {
//		return new Contact(SwaggerConstant.CONTACT_NAME, SwaggerConstant.CONTACT_URL, SwaggerConstant.CONTACT_EMAIL);
//	}
//	
//	private ApiKey apiKey() {
//		return new ApiKey("JWT", "Authorization", SecurityScheme.In.HEADER.name());
//	}
//	
//	private SecurityContext securityContext() {
//		return SecurityContext.builder().securityReferences(securityReference()).build();
//	}
//	
//	private List<SecurityReference> securityReference() {
//		AuthorizationScope[] authorizationScope = {new AuthorizationScope(SwaggerConstant.AUTHORIZATION_SCOPE, SwaggerConstant.AUTHORIZATION_DESCRIPTION)};
//		return 
//	}
//	List<String> names = Lists.newArrayList("John", "Adam", "Jane");
	
	@Bean
	public Docket newsApi() {
	    return new Docket(DocumentationType.SWAGGER_2)
	            .select()
	            .apis(RequestHandlerSelectors.basePackage("com.costrategix.gbp"))
	            .paths(PathSelectors.any())
	            .build()
	            .securitySchemes(Lists.newArrayList(apiKey()))
	            .securityContexts(Lists.newArrayList(securityContext()))
	            .apiInfo(apiInfo());
	}

	@Bean
	SecurityContext securityContext() {
	    return SecurityContext.builder()
	            .securityReferences(defaultAuth())
	            .forPaths(PathSelectors.any())
	            .build();
	}

	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope
	            = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Lists.newArrayList(
	            new SecurityReference("JWT", authorizationScopes));
	}

	private ApiKey apiKey() {
	    return new ApiKey("JWT", "Authorization", "header");
	}
	
	private ApiInfo apiInfo() {
	return new ApiInfo(SwaggerConstant.API_TITLE, SwaggerConstant.API_DESCRIPTION, SwaggerConstant.API_VERSION, SwaggerConstant.TERM_OF_SERVICE, contact(), 
			SwaggerConstant.LICENSE, SwaggerConstant.LICENSE_URL, Collections.emptyList());
	}
	
	private Contact contact() {
	return new Contact(SwaggerConstant.CONTACT_NAME, SwaggerConstant.CONTACT_URL, SwaggerConstant.CONTACT_EMAIL);
	}
	
}

