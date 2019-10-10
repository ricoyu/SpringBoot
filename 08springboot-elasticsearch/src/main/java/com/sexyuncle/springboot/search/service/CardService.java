package com.sexyuncle.springboot.search.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.search.entity.BaseballCard;

@Service
@Transactional
public class CardService {

    @Autowired
    private EntityOperations entityOperations;

    BaseballCard tedWilliams = new BaseballCard();
    BaseballCard bobGibson = new BaseballCard();
    BaseballCard honusWagner = new BaseballCard();

    public void addCards() {
        tedWilliams.setName("Ted Williams");
        tedWilliams.setYear(1954);
        tedWilliams.setRarityLevel("Very Rare");

        entityOperations.save(tedWilliams);

        bobGibson.setName("Bob Gibson");
        bobGibson.setYear(1959);
        bobGibson.setRarityLevel("Very Rare");

        entityOperations.save(bobGibson);

        honusWagner.setName("Honus Wagner");
        honusWagner.setYear(1909);
        honusWagner.setRarityLevel("Rarest");

        entityOperations.save(honusWagner);

        System.out.println("Cards have been added : " + entityOperations.findAll(BaseballCard.class));

    }
}