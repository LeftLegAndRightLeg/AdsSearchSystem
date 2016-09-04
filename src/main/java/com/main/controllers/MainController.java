package com.main.controllers;

/**
 * Created by gongbailiang on 9/1/16.
 */
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.main.algorithm.InvertedIndex;
import com.main.algorithm.ScoreRank;
import com.main.repository.AdRepository;
import com.main.repository.CampaignRepository;
import com.main.repository.ImageRepository;
import com.main.repository.ImageService;
import com.main.exception.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.main.models.Ad;
import com.main.models.AdImage;
import com.main.models.Campaign;
import com.main.queryParser.QueryParser;

@RestController
@EnableAutoConfiguration
public class MainController {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private InvertedIndex mapReduce;

    @Autowired
    private QueryParser queryParser;

    @Autowired
    private ScoreRank scoreRank;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value={"/searchAd/{keyWords}", "/searchAd/"},method = RequestMethod.GET)
    public String searchAd(@PathVariable("keyWords") String keyWords){

        if(keyWords.equals("null") || keyWords.equals("undefined")){
            throw  new KeyWordsNotFoundException();
        }

        List<Ad> res = new ArrayList<>();
        List<String> enteredKeyWords = queryParser.parseQuery(keyWords);
        mapReduce.map();
        scoreRank.setIndexMap(mapReduce.getMap());
        scoreRank.setEnteredKeyWords(enteredKeyWords);
        List<Long> adList = scoreRank.getScoreRank();

        for(long id : adList){
            res.add(adRepository.findOne(id));
        }
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        logger.info("****************************" + " searchAd " + "****************************");
        logger.info(gson.toJson(res));
        logger.info("****************************" + " Finishing " + "****************************");
        return gson.toJson(res);
    }


    @RequestMapping(value = "/getAllAd", method = RequestMethod.GET)
    public String getAllAd() {
        List<Ad> res = adRepository.findAll();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        logger.info("****************************" + " getAllAd " + "****************************");
        logger.info(gson.toJson(res));
        logger.info("****************************" + " Finishing " + "****************************");
        return gson.toJson(res);

    }

    @RequestMapping(value = "/getAllCampaign", method = RequestMethod.GET)
    public String getAllCampaign() {
        List<Campaign> res = campaignRepository.findAll();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        logger.info("****************************" + " getAllAd " + "****************************");
        logger.info(gson.toJson(res));
        logger.info("****************************" + " Finishing " + "****************************");
        return gson.toJson(res);

    }

    @RequestMapping(value="/deleteAd/{id}",method = RequestMethod.GET)
    public String deleteAd(@PathVariable("id") String id){
        if(id.equals("null") || id.equals("undefined")){
            throw new IdNotFoundException();
        }
        Ad cur = adRepository.findOne(Long.valueOf(id));
        if(cur == null){
            throw new AdNotFoundException();
        }
        imageService.deleteImageFromS3(cur.getAdImage());
        imageRepository.delete(cur.getAdImage());
        adRepository.delete(cur);

        List<Ad> res = adRepository.findAll();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        logger.info("****************************" + " deleteAd " + "****************************");
        logger.info(gson.toJson(res));
        logger.info("****************************" + " Finishing " + "****************************");
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

        Campaign campaign = campaignRepository.findOne(campaignId);
        AdImage image = imageService.saveFileToS3(adImage);
        imageRepository.save(image);

        Ad ad = new Ad(keyWords, name, description, image, pClick, bid, campaign);
        adRepository.save(ad);
        List<Ad> res = new ArrayList<Ad>();
        res.add(ad);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        logger.info("****************************" + " addAdd " + "****************************");
        logger.info(gson.toJson(res));
        logger.info("****************************" + " Finishing " + "****************************");

        return gson.toJson(res);
    }

    @RequestMapping(value = "/addCampaign", method = RequestMethod.POST)
    public String addCampaign(
            @RequestParam(value="name", required=true) String name,
            @RequestParam(value="budget", required=true) Float budget
    ) throws Exception {

        Campaign campaign = new Campaign(name, budget);
        campaignRepository.save(campaign);
        List<Campaign> res = new ArrayList<Campaign>();
        res.add(campaign);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        logger.info("****************************" + " addCampaign " + "****************************");
        logger.info(gson.toJson(res));
        logger.info("****************************" + " Finishing " + "****************************");

        return gson.toJson(res);
    }



}