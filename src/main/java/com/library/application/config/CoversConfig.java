package com.library.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("covers")
public class CoversConfig {

    private String url;
    private String cover;

    public CoversConfig() {
    }

    public CoversConfig(String url, String cover) {
        this.url = url;
        this.cover = cover;
    }
}
