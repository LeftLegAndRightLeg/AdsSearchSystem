package com;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dao.*;
import com.model.*;

@SpringBootApplication
public class WebAppInitializer implements CommandLineRunner{
	
	private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);

	
	@Autowired
	private CampaignDao campaignDao;
	
    public static void main(String[] args)  throws Exception{
    	
    	System.out.println("=====================" + " Initializing " + "=====================");
        SpringApplication.run(WebAppInitializer.class, args);
        System.out.println("=====================" + " Running " + "=====================");
    }
    
    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        // save a couple of categories
    	
    	
        Campaign categoryA = new Campaign("Category A", 100);
        Set<Ad> adsA = new HashSet<Ad>();
        adsA.add(new Ad("keyword1", 10, 20, categoryA));
        adsA.add(new Ad("keyword2", 10, 20, categoryA));
      
        categoryA.setAds(adsA);

        Campaign categoryB = new Campaign("Category B", 200);
        Set<Ad> adsB = new HashSet<Ad>();
        adsB.add(new Ad("keyword3", 10, 20, categoryB));
        adsB.add(new Ad("keyword4", 10, 20, categoryB));
        
        categoryB.setAds(adsB);
        
        Set<Campaign> categorySet = new HashSet<Campaign>();
        categorySet.add(categoryA);
        categorySet.add(categoryB);
        
        campaignDao.save(categorySet);
        

        // fetch all categories
        System.out.println("****************************" + " Printing " + "****************************");
        for (Campaign campaign : campaignDao.findAll()) {
            logger.info(campaign.toString());
        	System.out.println(campaign.toString());
        }
        System.out.println("****************************" + " Finishing " + "****************************");
    }
}

