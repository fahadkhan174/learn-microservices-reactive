package com.fmg.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fmg.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	public List<Order> findByUserId(Integer userId);

}
