package com.eye.tool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.eye.db","com.eye.mail","com.eye.tool"})
@MapperScan({"com.eye.db.dao"})
@EnableTransactionManagement
@EnableScheduling
public class EyeToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyeToolApplication.class,args);
    }
}
