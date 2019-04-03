package com.ouxuxi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.ouxuxi.dao")
@SpringBootApplication
public class OcprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcprojectApplication.class, args);
    }

}
