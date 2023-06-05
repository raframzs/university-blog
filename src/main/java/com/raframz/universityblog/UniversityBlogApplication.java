package com.raframz.universityblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@ConfigurationProperties("university-blog")
@Configuration
public class UniversityBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniversityBlogApplication.class, args);
    }

}
