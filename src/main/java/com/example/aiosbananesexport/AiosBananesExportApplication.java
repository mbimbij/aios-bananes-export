package com.example.aiosbananesexport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AiosBananesExportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiosBananesExportApplication.class, args);
    }

}
