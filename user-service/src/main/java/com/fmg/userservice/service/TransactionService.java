package com.fmg.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmg.userservice.dto.TransactionRequestDto;
import com.fmg.userservice.dto.TransactionResponseDto;
import com.fmg.userservice.dto.TransactionStatus;
import com.fmg.userservice.dto.UserTransactionDto;
import com.fmg.userservice.repository.UserRepository;
import com.fmg.userservice.repository.UserTransactionRepository;
import com.fmg.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserTransactionRepository userTransactionRepository;

  public Mono<TransactionResponseDto> insertTransaction(final TransactionRequestDto tReqDto) {
    return this.userRepository.updateUserBalance(tReqDto.getUserId(), tReqDto.getAmount())
        .filter(Boolean::booleanValue)
        .map(b -> EntityDtoUtil.toEntity(tReqDto))
        .flatMap(this.userTransactionRepository::save)
        .map(ut -> EntityDtoUtil.toDto(tReqDto, TransactionStatus.APPROVED))
        .defaultIfEmpty(EntityDtoUtil.toDto(tReqDto, TransactionStatus.DECLINED));
  }

  public Flux<UserTransactionDto> findByUserId(Integer userId) {
    return this.userTransactionRepository.findByUserId(userId).map(EntityDtoUtil::toDto);
  }

}
