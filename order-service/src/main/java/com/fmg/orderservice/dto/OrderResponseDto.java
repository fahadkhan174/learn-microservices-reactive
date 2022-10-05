package com.fmg.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

	private Integer id;
	private Integer userId;
	private String productId;
	private Integer amount;
	private OrderStatus status;
}
