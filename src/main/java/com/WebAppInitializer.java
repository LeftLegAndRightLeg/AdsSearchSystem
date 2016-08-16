package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dao.CampaignDao;

@SpringBootApplication
public class WebAppInitializer{
	
	@Autowired
	private CampaignDao test;
	
    public static void main(String[] args) throws Exception{
    	
    	System.out.println("=====================" + " Initializing " + "=====================");
        SpringApplication.run(WebAppInitializer.class, args);
        System.out.println("=====================" + " Running " + "=====================");
    }
}

