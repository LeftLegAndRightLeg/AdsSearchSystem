package com.main.repository;

/**
 * Created by gongbailiang on 9/1/16.
 */
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.models.Campaign;

@Transactional
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    public Campaign findByName(String name);
}