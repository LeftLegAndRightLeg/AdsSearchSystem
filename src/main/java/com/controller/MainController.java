package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algorithm.InvertedIndex;
import com.algorithm.ScoreRank;
import com.dao.AdDao;
import com.dao.CampaignDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Ad;
import com.queryParser.QueryParser;

@RestController
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
	
    @RequestMapping(value="/",method = RequestMethod.GET)
    public String homepage(){
        return "index";
    }
    
    @RequestMapping(value="/searchAd",method = RequestMethod.GET)
    public String searchAd(@RequestParam(value="keyWords", required=true) String keyWords){
    	
    	List<Ad> res = new ArrayList<Ad>();
    	List<String> enteredKeyWords = queryParser.parseQuery(keyWords);
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
    	System.out.println(res.toString());
    	System.out.println(gson.toJson(res));
    	System.out.println("****************************" + " Finishing " + "****************************");
        return gson.toJson(res);
    }
    
}
