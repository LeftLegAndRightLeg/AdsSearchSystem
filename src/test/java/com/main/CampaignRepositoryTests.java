package com.main;

import com.main.models.Campaign;
import com.main.repository.CampaignRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by gongbailiang on 9/4/16.
 */
//@RunWith(SpringRunner.class)
//@DataJpaTest
public class CampaignRepositoryTests {

//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private CampaignRepository campaignRepository;
//
//    @Test
//    public void findByNameTest(){
//        this.testEntityManager.persist(new Campaign("testCampaign", 123));
//        Campaign campaign = this.campaignRepository.findByName("testCampaign");
//
//        Assertions.assertThat(campaign.getName()).isEqualTo("testCampaign");
//        Assertions.assertThat(campaign.getBudget()).isEqualTo(123);
//    }
}
