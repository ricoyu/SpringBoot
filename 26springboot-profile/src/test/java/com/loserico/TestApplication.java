package com.loserico;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

/**
 * https://www.mkyong.com/spring-boot/spring-boot-profiles-example/
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html
 * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html
 * <p>
 * Copyright: Copyright (c) 2019-03-21 16:05
 * <p>
 * Company: Sexy Uncle Inc.
 * <p>
 * @author Rico Yu  ricoyu520@gmail.com
 * @version 1.0
 * @on
 */
public class TestApplication {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testDefaultProfile() {
		ProfileApplication.main(new String[0]);
		String output = this.outputCapture.toString();
		assertThat(output).contains("Today is sunny day!");
	}
	
	@Test
	public void testRainingProfile() {
		System.setProperty("spring.profiles.active", "raining");
		ProfileApplication.main(new String[0]);
        String output = this.outputCapture.toString();
        assertThat(output).contains("Today is raining day!");
	}
	
	@Test
	public void testRainingProfile_withDoption() {
		ProfileApplication.main(new String[] {"--spring.profiles.active=raining"});
		String output = outputCapture.toString();
		assertThat("Today is raining day!").contains("Today is raining day!");
	}
	
	@After
	public void after() {
		System.clearProperty("spring.profiles.active");
	}
}
