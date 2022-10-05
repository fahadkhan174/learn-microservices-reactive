package com.fmg.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fmg.userservice.dto.UserDto;
import com.fmg.userservice.repository.UserRepository;
import com.fmg.userservice.util.EntityDtoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public Flux<UserDto> findAll() {
    return this.userRepository.findAll().map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> insert(Mono<UserDto> userDtoMono) {
    return userDtoMono
        .map(EntityDtoUtil::toEntity)
        .flatMap(this.userRepository::save)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> findById(Integer id) {
    return this.userRepository.findById(id).map(EntityDtoUtil::toDto);
  }

  public Mono<UserDto> updateById(Integer id, Mono<UserDto> userDtoMono) {
    return this.userRepository
        .findById(id)
        .flatMap(user -> userDtoMono
            .map(EntityDtoUtil::toEntity)
            .doOnNext(e -> e.setId(id)))
        .flatMap(this.userRepository::save)
        .map(EntityDtoUtil::toDto);
  }

  public Mono<Void> deleteById(Integer id) {
    return this.userRepository.deleteById(id);
  }
}
