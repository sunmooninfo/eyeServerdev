package com.eye.brand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.eye.db", "com.eye.core","com.eye.storage","com.eye.brand"})
@MapperScan({"com.eye.db.dao"})
@EnableTransactionManagement
public class EyeBrandApplication {

    public static void main(String[] args) {
        SpringApplication.run(EyeBrandApplication.class,args);
    }

}
