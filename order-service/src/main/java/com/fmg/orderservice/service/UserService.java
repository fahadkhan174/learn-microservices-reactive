package com.fmg.orderservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fmg.orderservice.dto.TransactionRequestDto;
import com.fmg.orderservice.dto.TransactionResponseDto;
import com.fmg.orderservice.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	private final WebClient webClient;

	public UserService(@Value("${user.service.url}") String url) {
		this.webClient = WebClient.builder().baseUrl(url).build();
	}

	public Mono<TransactionResponseDto> attemptTransaction(TransactionRequestDto tReqDto) {
		return this.webClient.post().uri("/transaction").bodyValue(tReqDto).retrieve()
				.bodyToMono(TransactionResponseDto.class);
	}
	
	public Flux<UserDto> getAllUsers(){
		return this.webClient.get().uri("").retrieve().bodyToFlux(UserDto.class);
	}

}
