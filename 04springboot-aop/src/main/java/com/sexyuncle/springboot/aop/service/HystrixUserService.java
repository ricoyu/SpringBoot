package com.sexyuncle.springboot.aop.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HystrixUserService {

	@HystrixCommand
	public String findUserById(Long id) {
		return "俞雪华";
	}
	
	public static void main(String[] args) {
		System.out.println(".......");
	}
}
