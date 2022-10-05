package com.fmg.orderservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fmg.orderservice.dto.OrderStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "ORDERS")
public class Order {

	@Id
	@GeneratedValue
	private Integer id;
	private String productId;
	private Integer userId;
	private Integer amount;
	private OrderStatus status;

}
