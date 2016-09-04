package com.main.algorithm;

/**
 * Created by gongbailiang on 9/1/16.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.repository.*;
import com.main.models.*;

@Service
public class InvertedIndex {

    @Autowired
    private AdRepository adRepository;

    private Map<String, List<Long>> indexMap;

    public InvertedIndex(){
        indexMap = new HashMap<>();
    }

    public Map<String, List<Long>> getMap(){
        return this.indexMap;
    }

    public void map(){
        for(Ad ad : adRepository.findAll()){
            for(String key : ad.getKeyWords().split(",")){
                if(!indexMap.containsKey(key)){
                    indexMap.put(key, new ArrayList<>());
                }
                indexMap.get(key).add(ad.getId());
            }
        }
    }

}

