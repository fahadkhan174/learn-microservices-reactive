package com.fmg.productservice.config;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.fmg.productservice.dto.ProductDto;
import com.fmg.productservice.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class Initializer implements CommandLineRunner {

  @Autowired
  private ProductService productService;

  @Override
  public void run(String... args) throws Exception {
    ProductDto p1 = new ProductDto("4k TV", 1000);
    ProductDto p2 = new ProductDto("DSLR Camera", 750);
    ProductDto p3 = new ProductDto("iPhone", 800);
    ProductDto p4 = new ProductDto("headphone", 100);

    Flux.just(p1, p2, p3, p4)
        .concatWith(this.newProducts())
        .flatMap(p -> this.productService.insert(Mono.just(p)))
        .subscribe(System.out::println);

  }

  private Flux<ProductDto> newProducts() {
    return Flux.range(1, 1000)
        .delayElements(Duration.ofSeconds(1))
        .map(i -> new ProductDto("product" + i, ThreadLocalRandom.current().nextInt(10, 1000)));
  }

}
