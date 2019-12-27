package com.sexyuncle.springboot.testing;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 *
 * <p>
 * Copyright: (C), 2019/12/27 16:45
 * <p>
 * Company: Sexy Uncle Inc.
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class RandomPortWebAppTest {
	
	@LocalServerPort
	private int localServerPort;
	
	@Test
	public void testServerPort() {
		log.info("端口号: {}", localServerPort);
	}
}
