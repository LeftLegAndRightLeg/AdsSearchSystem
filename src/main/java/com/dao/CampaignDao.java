package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Campaign;

@Transactional
public interface CampaignDao extends JpaRepository<Campaign, Long> {

}