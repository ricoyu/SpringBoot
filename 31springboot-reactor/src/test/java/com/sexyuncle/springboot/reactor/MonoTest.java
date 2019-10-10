package com.sexyuncle.springboot.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoTest {

	@Test
	public void testJust() {
		Mono<String> just = Mono.just("foo");
		Publisher<String> pjust = Mono.just("foo");
	}

	@Test
	public void testCollectingElements() {
		List<Integer> elements = new ArrayList<>(4);
		Flux.just(1, 2, 3, 4)
				.log()
				.subscribe(elements::add);

		assertThat(elements).containsExactly(1, 2, 3, 4);
	}

	@Test
	public void testSubscribe() {
		List<Integer> elements = new ArrayList<>(4);
		Flux.just(1, 2, 3, 4)
				.log()
				.subscribe(new Subscriber<Integer>() {

					@Override
					public void onSubscribe(Subscription s) {
						s.request(Long.MAX_VALUE);
					}

					@Override
					public void onNext(Integer t) {
						elements.add(t);
					}

					@Override
					public void onError(Throwable t) {
					}

					@Override
					public void onComplete() {
					}

				});
	}

	@Test
	public void testBackpressure() {
		List<Integer> elements = new ArrayList<>(4);
		Flux.just(1, 2, 3, 4)
				.log()
				.subscribe(new Subscriber<Integer>() {

					private Subscription s;
					private int onNextAmount;

					@Override
					public void onSubscribe(Subscription s) {
						this.s = s;
						s.request(2);
					}

					@Override
					public void onNext(Integer t) {
						elements.add(t);
						onNextAmount++;
						if (onNextAmount % 2 == 0) {
							s.request(2);
						}
					}

					@Override
					public void onError(Throwable t) {
					}

					@Override
					public void onComplete() {
					}

				});
	}
}
