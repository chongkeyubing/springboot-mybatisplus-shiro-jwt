package com.company.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.company.project.**.mapper")   //扫描所有mapper文件夹
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
