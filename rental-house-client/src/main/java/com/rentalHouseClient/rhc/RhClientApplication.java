package com.rentalHouseClient.rhc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 10098
 */
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

@ComponentScan(basePackages = {"com.rentalHouseClient.rhc.*"})
@EnableTransactionManagement
public class RhClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(RhClientApplication.class, args);
    }

}
