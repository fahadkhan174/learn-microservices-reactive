package com.fmg.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fmg.orderservice.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	private final WebClient webClient;

	public ProductService(@Value("${product.service.url}") String url) {
		this.webClient = WebClient.builder().baseUrl(url).build();
	}

	public Mono<ProductDto> getProductById(final String productId) {
		return this.webClient.get().uri("{id}", productId).retrieve().bodyToMono(ProductDto.class);
	}
	
	public Flux<ProductDto> getAllProducts(){
		return this.webClient.get().uri("").retrieve().bodyToFlux(ProductDto.class);
	}

}
