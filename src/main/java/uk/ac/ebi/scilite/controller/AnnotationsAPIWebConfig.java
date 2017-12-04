package uk.ac.ebi.scilite.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AnnotationsAPIWebConfig extends WebMvcConfigurerAdapter {

  /**
    *  Total customization - see below for explanation.
    */
  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false).
            favorParameter(true).
            parameterName("format").
            ignoreAcceptHeader(true).
            useJaf(false).
            defaultContentType(MediaType.APPLICATION_JSON).
            mediaType("xml", MediaType.APPLICATION_XML).
            mediaType("json", MediaType.APPLICATION_JSON).
            mediaType("json_ld", MediaType.APPLICATION_JSON).
            mediaType("json-ld", MediaType.APPLICATION_JSON).
            mediaType("id_list", MediaType.APPLICATION_JSON).
            mediaType("tsv",MediaType.APPLICATION_JSON).
            mediaType("html",MediaType.TEXT_HTML);
  }
}