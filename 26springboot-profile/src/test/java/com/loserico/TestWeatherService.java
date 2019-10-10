package com.loserico;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loserico.service.WeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("raining")
public class TestWeatherService {

	@Autowired
	private WeatherService weatherService;

	@Test
	public void testRainingProfile() {
		String output = weatherService.forecast();
		Assertions.assertThat(output).contains("Today is raining day!");
	}
}
