package com.eye.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.eye.db", "com.eye.core", "com.eye.common","com.eye.storage","com.eye.mail","com.eye.sms","com.eye.express"})
@MapperScan({"com.eye.db.dao"})
@EnableTransactionManagement
@EnableScheduling
public class EyeCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyeCommonApplication.class,args);
    }
}
