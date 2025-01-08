package com.jelwery.morri.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  
                .allowedOrigins("http://localhost:3000")
                // .allowedOrigins("http://localhost:3005")
                // .allowedOrigins("http://localhost:3001")
                // .allowedOrigins("http://localhost:3006")
                

                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") 
                .allowedHeaders("*")  
                .allowCredentials(true); 
    }
}
