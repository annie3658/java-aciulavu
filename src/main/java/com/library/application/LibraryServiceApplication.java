package com.library.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class LibraryServiceApplication {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LibraryServiceApplication.class);

    @Bean
    public WebClient.Builder getWebClientBuilder(){
        return  WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
        LOGGER.info("Application started");
    }

}
