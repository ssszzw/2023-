package com.example20231016.RBAC_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RedisConfig.class)
public class Todo3Application {

    public static void main(String[] args) {
        SpringApplication.run(Todo3Application.class, args);
    }

}
