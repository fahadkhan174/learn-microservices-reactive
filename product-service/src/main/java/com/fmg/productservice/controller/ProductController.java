package com.fmg.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fmg.productservice.dto.ProductDto;
import com.fmg.productservice.service.ProductService;

import io.netty.util.internal.ThreadLocalRandom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private Flux<ProductDto> flux;

	@GetMapping
	public Flux<ProductDto> findAll() {
		return this.productService.findAll();
	}

	@PostMapping
	public Mono<ProductDto> insert(@RequestBody Mono<ProductDto> productDto) {
		return this.productService.insert(productDto);
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<ProductDto>> findById(@PathVariable("id") String id) {
		this.randomException();
		return this.productService.findById(id).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PutMapping("{id}")
	public Mono<ResponseEntity<ProductDto>> save(@PathVariable("id") String id,
			@RequestBody Mono<ProductDto> productDto) {
		return this.productService.save(id, productDto).map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public Mono<Void> deleteById(@PathVariable("id") String id) {
		return this.productService.deleteById(id);
	}

	@GetMapping("price-range")
	public Flux<ProductDto> findByPriceBetween(@RequestParam("min") Integer min, @RequestParam("max") Integer max) {
		return this.productService.findByPriceBetween(min, max);
	}

	private void randomException() {
		int nextInt = ThreadLocalRandom.current().nextInt(1, 10);
		if (nextInt > 5) {
			throw new RuntimeException();
		}
	}

	@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDto> productStream() {
		return this.flux;
	}

	@GetMapping(value = "/stream/{maxPrice}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ProductDto> productStream(@PathVariable Integer maxPrice) {
		return this.flux
				.filter(pDto -> pDto.getPrice() <= maxPrice);
	}

}
