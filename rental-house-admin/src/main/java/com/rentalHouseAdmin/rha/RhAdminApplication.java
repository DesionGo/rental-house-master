package com.rentalHouseAdmin.rha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class RhAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(RhAdminApplication.class, args);
    }

}
