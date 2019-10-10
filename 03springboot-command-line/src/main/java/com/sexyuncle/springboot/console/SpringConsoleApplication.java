package com.sexyuncle.springboot.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sexyuncle.springboot.console.service.HelloMessageService;

@SpringBootApplication
public class SpringConsoleApplication implements CommandLineRunner{

	@Autowired
	private HelloMessageService helloMessageService;

	public static void main(String[] args) {
		/*
		不显示banner
		SpringApplication springApplication = new SpringApplication(SpringHelloMessageConsoleApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
		*/
		SpringApplication.run(SpringConsoleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0) {
			System.out.println(helloMessageService.getMessage(args[0].toString()));
		} else {
			System.out.println(helloMessageService.getMessage());
		}
	}


}
