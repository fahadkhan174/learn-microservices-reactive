package com.fmg.userservice.util;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.fmg.userservice.dto.TransactionRequestDto;
import com.fmg.userservice.dto.TransactionResponseDto;
import com.fmg.userservice.dto.TransactionStatus;
import com.fmg.userservice.dto.UserDto;
import com.fmg.userservice.dto.UserTransactionDto;
import com.fmg.userservice.entity.User;
import com.fmg.userservice.entity.UserTransaction;

public class EntityDtoUtil {

    private EntityDtoUtil() {
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDto tReqDto) {
        UserTransaction ut = new UserTransaction();
        ut.setUserId(tReqDto.getUserId());
        ut.setAmount(tReqDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto tReqDto, TransactionStatus status) {
        TransactionResponseDto tResDto = new TransactionResponseDto();
        tResDto.setUserId(tReqDto.getUserId());
        tResDto.setAmount(tReqDto.getAmount());
        tResDto.setStatus(status);
        return tResDto;
    }

    public static UserTransaction toEntity(UserTransactionDto utDto) {
        UserTransaction ut = new UserTransaction();
        BeanUtils.copyProperties(utDto, ut);
        return ut;
    }

    public static UserTransactionDto toDto(UserTransaction ut) {
        UserTransactionDto utDto = new UserTransactionDto();
        BeanUtils.copyProperties(ut, utDto);
        return utDto;
    }

}
