package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "adimage")
public class AdImage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private long id;
	  
	@Expose
	private String keyValue;
	  
	@Expose
	private String url;
	
	@OneToOne(mappedBy = "adImage")
	private Ad ad;
	
	public AdImage() {
		super();
	}

	public AdImage(long id) {
		super();
		this.id = id;
	}

	public AdImage(String keyValue, String url) {
		super();
		this.keyValue = keyValue;
		this.url = url;
	}


	public Ad getAd() {
		return ad;
	}

	public void setAd(Ad ad) {
		this.ad = ad;
	}

	public String getkeyValue() {
		return keyValue;
	}

	public void setkeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
		
}