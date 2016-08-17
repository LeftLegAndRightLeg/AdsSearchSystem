package com.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.*;
import com.model.Ad;

@Service
public class InvertedIndex {
	
	@Autowired
	private AdDao adDao;
	
	private Map<String, List<Long>> indexMap;
	
	public InvertedIndex(){
		indexMap = new HashMap<String, List<Long>>();
	}
	
	public void mapReduce(){
		for(Ad ad : adDao.findAll()){
			for(String key : ad.getKeyWords().split(",")){
				if(!indexMap.containsKey(key)){
					indexMap.put(key, new ArrayList<Long>());
				}
				indexMap.get(key).add(ad.getId());
			}
		}
	}
	public String printMap(){
		return indexMap.toString();
	}
}
