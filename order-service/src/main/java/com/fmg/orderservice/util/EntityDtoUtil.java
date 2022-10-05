package com.fmg.orderservice.util;

import org.springframework.beans.BeanUtils;

import com.fmg.orderservice.dto.OrderResponseDto;
import com.fmg.orderservice.dto.OrderStatus;
import com.fmg.orderservice.dto.RequestContext;
import com.fmg.orderservice.dto.TransactionRequestDto;
import com.fmg.orderservice.dto.TransactionStatus;
import com.fmg.orderservice.entity.Order;

public class EntityDtoUtil {

	private EntityDtoUtil() {
	}

	public static void setTransactionRequestDto(RequestContext rc) {
		TransactionRequestDto tReqDto = new TransactionRequestDto();
		tReqDto.setUserId(rc.getOrderRequestDto().getUserId());
		tReqDto.setAmount(rc.getProductDto().getPrice());
		rc.setTransactionRequestDto(tReqDto);
	}

	public static Order getOrder(RequestContext rc) {
		Order order = new Order();
		order.setUserId(rc.getTransactionRequestDto().getUserId());
		order.setProductId(rc.getOrderRequestDto().getProductId());
		order.setAmount(rc.getProductDto().getPrice());
		TransactionStatus status = rc.getTransactionResponseDto().getStatus();
		OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED
				: OrderStatus.FAILED;
		order.setStatus(orderStatus);
		return order;
	}

	public static OrderResponseDto toDto(Order order) {
		OrderResponseDto oResDto = new OrderResponseDto();
		BeanUtils.copyProperties(order, oResDto);
		return oResDto;
	}

}
