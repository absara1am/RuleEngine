package com.zeotap.rule_engine.RuleEngine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
          .addMapping("/**")
          .allowedOrigins(
            "http://127.0.0.1:5500",
            "https://ab-ruleengine-sar.netlify.app/"
          )
          .allowedMethods("GET", "POST", "PUT", "DELETE")
          .allowedHeaders("*")
          .allowCredentials(true);
      }
    };
  }
}
