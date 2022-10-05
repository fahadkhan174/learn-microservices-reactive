package com.fmg.orderservice.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmg.orderservice.dto.OrderRequestDto;
import com.fmg.orderservice.dto.OrderResponseDto;
import com.fmg.orderservice.dto.RequestContext;
import com.fmg.orderservice.repository.OrderRepository;
import com.fmg.orderservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

@Service
public class OrderService {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderRepository orderRepository;

	public Mono<OrderResponseDto> processOrder(Mono<OrderRequestDto> oReqDto){
		return oReqDto
			.map(RequestContext::new)
			.flatMap(this::productRequestResponse)
			.doOnNext(EntityDtoUtil::setTransactionRequestDto)
			.flatMap(this::userRequestResponse)
			.map(EntityDtoUtil::getOrder)
			.map(this.orderRepository::save) // blocking
			.map(EntityDtoUtil::toDto)
			.subscribeOn(Schedulers.boundedElastic()); // to execute blocking code in dedicated thread

	}

	private Mono<RequestContext> productRequestResponse(RequestContext rc) {
		return this.productService
				.getProductById(rc.getOrderRequestDto().getProductId())
				.doOnNext(rc::setProductDto)
				.retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
				.thenReturn(rc);
	}

	private Mono<RequestContext> userRequestResponse(RequestContext rc) {
		return this.userService.attemptTransaction(rc.getTransactionRequestDto())
				.doOnNext(rc::setTransactionResponseDto).thenReturn(rc);
	}

	public Flux<OrderResponseDto> findByUserId(Integer userId) {
		return Flux.fromStream(() -> this.orderRepository.findByUserId(userId).stream()).map(EntityDtoUtil::toDto)
				.subscribeOn(Schedulers.boundedElastic());
	}
}
