package com.main.models;

/**
 * Created by gongbailiang on 9/1/16.
 */
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;




@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    private long id;

    @Expose
    private String keyWords;

    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adimage_id")
    private AdImage adImage;


    @Expose
    private float pClick;

    @Expose
    private float bid;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    public Ad() {
        super();
    }

    public Ad(long id) {
        super();
        this.id = id;
    }


    public Ad(String keyWords, String name, String description, AdImage adImage, float pClick, float bid,
              Campaign campaign) {
        super();
        this.keyWords = keyWords;
        this.name = name;
        this.description = description;
        this.adImage = adImage;
        this.pClick = pClick;
        this.bid = bid;
        this.campaign = campaign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AdImage getAdImage() {
        return adImage;
    }

    public void setAdImage(AdImage adImage) {
        this.adImage = adImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public float getpClick() {
        return pClick;
    }

    public void setpClick(float pClick) {
        this.pClick = pClick;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }


    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ad [id=" + id + ", keyWords=" + keyWords + ", pClick=" + pClick + ", bid=" + bid + ", campaign="
                + campaign.getName() + "]");
        return sb.toString();

    }



//   ------------------------
//   PUBLIC METHODS
//   ------------------------



}