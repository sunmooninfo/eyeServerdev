package com.eye.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.eye.db", "com.eye.core", "com.eye.cms", "com.eye.storage"})
@MapperScan({"com.eye.db.dao"})
@EnableTransactionManagement
public class EyeCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyeCmsApplication.class, args);
    }
}
