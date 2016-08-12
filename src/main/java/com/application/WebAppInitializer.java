package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebAppInitializer{

    public static void main(String[] args) throws Exception{
    	
    	System.out.println("=====================" + " Initializing " + "=====================");
        SpringApplication.run(WebAppInitializer.class, args);
        System.out.println("=====================" + " Running " + "=====================");
    }
}

