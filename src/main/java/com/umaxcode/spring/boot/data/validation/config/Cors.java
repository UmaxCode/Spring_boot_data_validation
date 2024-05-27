package com.umaxcode.spring.boot.data.validation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Cors implements WebMvcConfigurer {

  public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
              .allowedOrigins("*")
              .maxAge(3600)
              .allowedHeaders("*")
              .allowedMethods("*");
  }

}
