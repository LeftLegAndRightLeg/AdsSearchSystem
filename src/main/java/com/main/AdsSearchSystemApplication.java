package com.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.transaction.annotation.Transactional;
import com.main.algorithm.InvertedIndex;

@SpringBootApplication
public class AdsSearchSystemApplication implements CommandLineRunner{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());




	@Autowired
	private InvertedIndex mapReduce;

	public static void main(String[] args)  throws Exception{

		SpringApplication.run(AdsSearchSystemApplication.class, args);

	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {
		// Initial Codes Here
        logger.info("=====================" + " Initializing " + "=====================");
		logger.info("****************************" + " MapReduce " + "****************************");
		mapReduce.map();
		logger.info("****************************" + " Finishing " + "****************************");
        logger.info("=====================" + " Running " + "=====================");
	}
}


