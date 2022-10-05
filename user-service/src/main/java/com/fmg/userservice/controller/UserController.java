package com.fmg.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmg.userservice.dto.UserDto;
import com.fmg.userservice.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping
  public Flux<UserDto> findAll() {
    return this.userService.findAll();
  }

  @PostMapping
  public Mono<UserDto> insert(@RequestBody Mono<UserDto> userDtoMono) {
    return this.userService.insert(userDtoMono);
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<UserDto>> findById(@PathVariable("id") Integer id) {
    return this.userService.findById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<UserDto>> updateById(@PathVariable("id") Integer id,
      @RequestBody Mono<UserDto> userDtoMono) {
    return this.userService.updateById(id, userDtoMono)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public Mono<Void> deleteById(@PathVariable("id") Integer id) {
    return this.userService.deleteById(id);
  }
}
