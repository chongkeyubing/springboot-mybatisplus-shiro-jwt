package com.company.basemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.company.basemanager.**.mapper")   //扫描所有mapper文件夹
public class BasemanagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasemanagerApplication.class, args);
    }
}
