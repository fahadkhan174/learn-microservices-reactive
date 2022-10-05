package com.fmg.userservice.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionDto {

  private Integer id;
  private Integer userId;
  private Integer amount;
  private LocalDateTime transactionDate;

}
