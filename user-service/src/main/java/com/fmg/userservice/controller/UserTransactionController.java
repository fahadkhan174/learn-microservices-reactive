package com.fmg.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fmg.userservice.dto.TransactionRequestDto;
import com.fmg.userservice.dto.TransactionResponseDto;
import com.fmg.userservice.dto.UserTransactionDto;
import com.fmg.userservice.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
public class UserTransactionController {

  @Autowired
  private TransactionService transactionService;

  @PostMapping
  public Mono<TransactionResponseDto> insertTransaction(@RequestBody Mono<TransactionRequestDto> tReqDtoMono) {
    return tReqDtoMono.flatMap(this.transactionService::insertTransaction);
  }

  @GetMapping
  public Flux<UserTransactionDto> findByUserId(@RequestParam("userId") Integer userId) {
    return this.transactionService.findByUserId(userId);
  }

}
