package com.library.application;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryServiceApplication {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(LibraryServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LibraryServiceApplication.class, args);
        LOGGER.info("Application started");
    }

}
