package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algorithm.InvertedIndex;
import com.algorithm.ScoreRank;
import com.dao.AdDao;
import com.dao.CampaignDao;
import com.dao.ImageDao;
import com.dao.ImageService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Ad;
import com.model.AdImage;
import com.model.Campaign;
import com.queryParser.QueryParser;

@RestController
@EnableAutoConfiguration
public class MainController {
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private InvertedIndex mapReduce;
	
	@Autowired
	private QueryParser queryParser;
	
	@Autowired
	private ScoreRank scoreRank;
	
	@Autowired
	private AdDao adDao;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private ImageDao imageDao;
	
	
    @RequestMapping(value="/searchAd/{keyWords}",method = RequestMethod.GET)
    public String searchAd(@PathVariable("keyWords") String keyWords){
    	
    	List<Ad> res = new ArrayList<Ad>();
    	List<String> enteredKeyWords = queryParser.parseQuery(keyWords);
    	mapReduce.map();
    	scoreRank.setIndexMap(mapReduce.getMap());
    	scoreRank.setEnteredKeyWords(enteredKeyWords);
    	List<Long> adList = scoreRank.getScoreRank();
    	
    	for(long id : adList){
    		res.add(adDao.findOne(id));
    	}
    	Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    	System.out.println("****************************" + " searchAd " + "****************************");
    	System.out.println(gson.toJson(res));
    	System.out.println("****************************" + " Finishing " + "****************************");
        return gson.toJson(res);
    }
    
    
    @RequestMapping(value = "/getAllAd", method = RequestMethod.GET)
	public String getAllAd() {
		List<Ad> res = adDao.findAll();
		Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
		System.out.println("****************************" + " getAllAd " + "****************************");
		System.out.println(gson.toJson(res));
		System.out.println("****************************" + " Finishing " + "****************************");
		return gson.toJson(res);
		
	}
    
    @RequestMapping(value = "/getAllCampaign", method = RequestMethod.GET)
	public String getAllCampaign() {
		List<Campaign> res = campaignDao.findAll();
		Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
		System.out.println("****************************" + " getAllAd " + "****************************");
		System.out.println(gson.toJson(res));
		System.out.println("****************************" + " Finishing " + "****************************");
		return gson.toJson(res);
		
	}
    
    @RequestMapping(value = "/addAd", method = RequestMethod.POST)
    public String addAdd(
    		@RequestParam(value="adImage", required=true) MultipartFile adImage,
    		@RequestParam(value="keyWords", required=true) String keyWords,
            @RequestParam(value="name", required=true) String name,
            @RequestParam(value="description", required=true) String description,
            @RequestParam(value="pClick", required=true) Float pClick,
            @RequestParam(value="bid", required=true) Float bid,
            @RequestParam(value="campaignId", required=true) Long campaignId 
            ) throws Exception {
    	
    	Campaign campaign = campaignDao.findOne(campaignId);
    	AdImage image = imageService.saveFileToS3(adImage);
    	imageDao.save(image);
    	
    	Ad ad = new Ad(keyWords, name, description, image, pClick, bid, campaign);
    	adDao.save(ad);
    	List<Ad> res = new ArrayList<Ad>();
    	res.add(ad);
    	Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    	System.out.println("****************************" + " addAdd " + "****************************");
    	System.out.println(gson.toJson(res));
    	System.out.println("****************************" + " Finishing " + "****************************");
    	
    	return gson.toJson(res);
    }
    
    @RequestMapping(value = "/addCampaign", method = RequestMethod.POST)
    public String addCampaign(
    		@RequestParam(value="name", required=true) String name,
    		@RequestParam(value="budget", required=true) Float budget
            ) throws Exception {
    	
    	Campaign campaign = new Campaign(name, budget);
    	campaignDao.save(campaign);
    	List<Campaign> res = new ArrayList<Campaign>();
    	res.add(campaign);
    	Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    	System.out.println("****************************" + " addCampaign " + "****************************");
    	System.out.println(gson.toJson(res));
    	System.out.println("****************************" + " Finishing " + "****************************");
    	
    	return gson.toJson(res);
    }
    
    
    
}
