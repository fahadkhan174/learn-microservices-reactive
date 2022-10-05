package com.fmg.orderservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fmg.orderservice.dto.OrderRequestDto;
import com.fmg.orderservice.dto.OrderResponseDto;
import com.fmg.orderservice.dto.ProductDto;
import com.fmg.orderservice.dto.UserDto;
import com.fmg.orderservice.service.OrderService;
import com.fmg.orderservice.service.ProductService;
import com.fmg.orderservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Test
	void contextLoads() {
		Flux<OrderResponseDto> oResDtoFlux = Flux
				.zip(this.userService.getAllUsers(), this.productService.getAllProducts())
				.map(t -> buildOrderRequestDto(t.getT1(), t.getT2()))
				.flatMap(oReqDto -> this.orderService.processOrder(Mono.just(oReqDto))).doOnNext(System.out::println);

		StepVerifier.create(oResDtoFlux).expectNextCount(4).verifyComplete();
	}

	private OrderRequestDto buildOrderRequestDto(UserDto userDto, ProductDto productDto) {
		OrderRequestDto oReqDto = new OrderRequestDto();
		oReqDto.setUserId(userDto.getId());
		oReqDto.setProductId(productDto.getId());
		return oReqDto;
	}

}
