package com.eye.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.eye.db","com.eye.mail","com.eye.sms",
		"com.eye.core","com.eye.admin","com.eye.storage","com.eye.express"})
@MapperScan({"com.eye.db.dao"})
@EnableTransactionManagement
@EnableScheduling
public class EyeAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyeAdminApplication.class,args);
    }
}
