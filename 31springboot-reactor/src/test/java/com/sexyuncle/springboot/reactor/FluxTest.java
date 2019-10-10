package com.sexyuncle.springboot.reactor;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class FluxTest {

	@Test
	public void testJust() {
		Flux<String> just = Flux.just("1", "2", "3");
	}
}
