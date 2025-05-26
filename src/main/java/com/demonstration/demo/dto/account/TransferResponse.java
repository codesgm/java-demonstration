package com.demonstration.demo.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class TransferResponse {
    private boolean success;
    private String message;
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
}
