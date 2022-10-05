package com.fmg.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fmg.orderservice.dto.OrderRequestDto;
import com.fmg.orderservice.dto.OrderResponseDto;
import com.fmg.orderservice.service.OrderService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public Mono<ResponseEntity<OrderResponseDto>> order(@RequestBody Mono<OrderRequestDto> orderRequestDtoMono) {
		return this.orderService.processOrder(orderRequestDtoMono).map(ResponseEntity::ok)
				.onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
				.onErrorReturn(WebClientRequestException.class, ResponseEntity.badRequest().build());
	}

	@GetMapping("user/{userId}")
	public Flux<OrderResponseDto> findByUserId(@PathVariable("userId") Integer userId) {
		return this.orderService.findByUserId(userId);
	}
}
