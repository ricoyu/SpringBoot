package com.sexyuncle.springboot.aop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sexyuncle.springboot.aop.annotation.TrackTime;

@Service
public class Business1 {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @TrackTime
    public String calculateSomething() {
        String value = "Data from Business1";
        logger.info("In Business - {}", value);
        return value;
    }
}