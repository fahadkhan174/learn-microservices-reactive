package com.fmg.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.fmg.productservice.dto.ProductDto;
import com.fmg.productservice.repository.ProductRepository;
import com.fmg.productservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private Sinks.Many<ProductDto> sink;

	public Flux<ProductDto> findAll() {
		return this.productRepository.findAll().map(EntityDtoUtil::toDto);
	}

	public Mono<ProductDto> insert(Mono<ProductDto> productDto) {
		return productDto.map(EntityDtoUtil::toEntity).flatMap(this.productRepository::insert).map(EntityDtoUtil::toDto)
				.doOnNext(this.sink::tryEmitNext);
	}

	public Mono<ProductDto> findById(String id) {
		return this.productRepository.findById(id).map(EntityDtoUtil::toDto);
	}

	public Mono<ProductDto> save(String id, Mono<ProductDto> productDto) {
		return this.productRepository.findById(id)
				.flatMap(product -> productDto.map(EntityDtoUtil::toEntity).doOnNext(e -> e.setId(id)))
				.flatMap(this.productRepository::save).map(EntityDtoUtil::toDto);
	}

	public Mono<Void> deleteById(String id) {
		return this.productRepository.deleteById(id);
	}

	public Flux<ProductDto> findByPriceBetween(Integer min, Integer max) {
		return this.productRepository.findByPriceBetween(Range.closed(min, max)).map(EntityDtoUtil::toDto);
	}

}
