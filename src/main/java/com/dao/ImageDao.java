package com.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.AdImage;

@Transactional
public interface ImageDao extends JpaRepository<AdImage, Long> {

}