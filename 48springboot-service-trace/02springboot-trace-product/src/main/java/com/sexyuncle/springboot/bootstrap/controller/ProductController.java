package com.sexyuncle.springboot.bootstrap.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * <p>
 * Copyright: (C), 2019/12/7 21:11
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping
@Slf4j
public class ProductController {
	
	@Resource(name = "traceThreadPoolExecutor")
	private Executor executor;
	
	@PostMapping("/deduct")
	public String deduct(Integer count) throws InterruptedException {
		log.info("扣库存:{}", count);
		
		CountDownLatch countDownLatch = new CountDownLatch(1);
		executor.execute(() -> {
			log.info("这是在线程中执行任务");
			countDownLatch.countDown();
		});
		countDownLatch.await();
		return "成功扣库存: " + count;
	}
}
