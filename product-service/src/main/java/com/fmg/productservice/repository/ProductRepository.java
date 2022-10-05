package com.fmg.productservice.repository;

import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.fmg.productservice.entity.Product;

import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    // these min and max are exclusive values and not equal not considered
    // Flux<Product> findByPriceBetween(Integer min, Integer max);

    Flux<Product> findByPriceBetween(Range<Integer> range);

}
