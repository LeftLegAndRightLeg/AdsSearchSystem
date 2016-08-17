package com.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AdDao;

//Relevance Score = hitCount / keyWordSize        relevance score > 0.3
//Quality Score = 0.75 * pClick + 0.25 * Relevance Score
//Rank Score = Quality Score * bid                bid > 4.5
//
//hitCount // the word size user entered
//
//keyWordSize // the key word size of ad
//Top 4

@Service
public class ScoreRank {
	private Map<String, List<Long>> indexMap;
	private List<String> enteredKeyWords;
	
	@Autowired
	private AdDao adDao;
	
	public ScoreRank(){
		
	}
	
	public Map<String, List<Long>> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<String, List<Long>> indexMap) {
		this.indexMap = indexMap;
	}

	public List<String> getEnteredKeyWords() {
		return enteredKeyWords;
	}

	public void setEnteredKeyWords(List<String> enteredKeyWords) {
		this.enteredKeyWords = enteredKeyWords;
	}

	
	public List<Long> getScoreRank(){
		Map<Long, AdScore> map = new HashMap<Long, AdScore>();
		List<AdScore> list = new ArrayList<AdScore>();
		List<Long> res = new ArrayList<Long>();
		int keyWordSize = enteredKeyWords.size();
		// calculate hitCount
		for(String str : enteredKeyWords){
			for(long id : indexMap.get(str)){
				if(!map.containsKey(id)){
					map.put(id, new AdScore(id, 0));
				}
				map.get(id).hitCount++;
			}
		}
		// calculate relevance
		for(AdScore cur : map.values()){
			cur.relevance = cur.hitCount / keyWordSize;
			if(cur.relevance > 0.3){
				list.add(cur);
			}
		}
		// calculate quality
		for(AdScore cur : list){
			float pClick = adDao.findOne(cur.id).getpClick();
			cur.quality = (float)(0.75 * pClick + 0.25 * cur.relevance);
		}
		// calculate rank
		for(AdScore cur : list){
			cur.rank = cur.quality * adDao.findOne(cur.id).getBid();
		}
		// sort based on rank score
		Collections.sort(list, new AdComparator());
		
		// need to add filter
		
		// return top 5
		
		for(AdScore cur : list){
			if(res.size() > 3){
				break;
			}
			res.add(cur.id);
		}
		return res;
	}
	
	
	class AdComparator implements Comparator<AdScore>{
		@Override
		public int compare(AdScore l, AdScore r){
			return (int)(r.rank - l.rank);
		}
	}
	
	class AdScore{
		long id;
		int hitCount;
		float relevance;
		float quality;
		float rank;
		
		AdScore(){	
		}

		public AdScore(long id, int hitCount) {
			super();
			this.id = id;
			this.hitCount = hitCount;
		}
	}
	
}
