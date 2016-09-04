package com.main.models;

/**
 * Created by gongbailiang on 9/1/16.
 */
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "campaign")
public class Campaign {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Expose
    private long id;

    @Expose
    private String name;

    @Expose
    private float budget;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<Ad> Ads;

    public Campaign() {
        super();
    }
    public Campaign(String name, float budget) {
        super();
        this.name = name;
        this.budget = budget;
    }
    public Campaign(long id) {
        super();
        this.id = id;
    }


    public void setAds(Set<Ad> ads) {
        Ads = ads;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getBudget() {
        return budget;
    }
    public void setBudget(float budget) {
        this.budget = budget;
    }

    public Set<Ad> getAds() {
        return Ads;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Campaign [id=" + id + ", name=" + name + ", budget=" + budget);
        sb.append(", Ads= ");
        for(Ad ad : Ads){
            sb.append(ad.getId() + " ");
        }
        sb.append(" ]");
        return sb.toString();

    }


}