package com;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import com.algorithm.InvertedIndex;

@SpringBootApplication
public class WebAppInitializer implements CommandLineRunner{
	
	
	@Autowired
	private InvertedIndex mapReduce;
	
    public static void main(String[] args)  throws Exception{
    	
    	System.out.println("=====================" + " Initializing " + "=====================");
        SpringApplication.run(WebAppInitializer.class, args);
        System.out.println("=====================" + " Running " + "=====================");
    }
    
    @Override
    @Transactional
    public void run(String... strings) throws Exception {
    	// Initail Codes Here
        System.out.println("****************************" + " MapReduce " + "****************************");
        mapReduce.map();
        System.out.println(mapReduce.getMap());

        System.out.println("****************************" + " Finishing " + "****************************");
    }
}

