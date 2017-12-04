package uk.ac.ebi.scilite.controller;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;

import springfox.documentation.service.Contact;

import springfox.documentation.spi.DocumentationType;

import springfox.documentation.spring.web.plugins.Docket;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Configuration
@EnableSwagger2
public class AnnotationsAPISwaggerConfig {
	
    @Bean
    public Docket productApi() {

    	Set<String> protocols=new HashSet<String>();
    	protocols.add("https");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("uk.ac.ebi.scilite.controller"))
                .paths(regex("/annotations.*"))
                .build().
                protocols(protocols)
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {

    	String version ="Unknown";
    	Properties prop = new Properties();
		
		try (InputStream inputStream = AnnotationsAPISwaggerConfig.class.getClassLoader().getResourceAsStream("build.properties");){
			prop.load(inputStream);
			version = prop.getProperty("build.version");
		} catch (IOException e) {}
		
        ApiInfo apiInfo = new ApiInfo(
                "Europe PMC Annotations API",
                "Europe PMC Annotations API provides text mining annotations contained in abstracts and open access full text articles, using the <a href=\"https://www.w3.org/TR/annotation-model/\" target=\"_blank\">W3C Open Annotation Data Model</a>",
                version,
                null,
                new Contact("Europe PMC", null, "helpdesk@europepmc.org"),
               "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");

        return apiInfo;

    }

}