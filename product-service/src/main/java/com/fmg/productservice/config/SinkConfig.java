package com.fmg.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fmg.productservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

	@Bean
	public Sinks.Many<ProductDto> sink() {
		return Sinks.many().replay().limit(1);
	}

	@Bean
	public Flux<ProductDto> productStream(Sinks.Many<ProductDto> sink) {
		return sink.asFlux();
	}

}
