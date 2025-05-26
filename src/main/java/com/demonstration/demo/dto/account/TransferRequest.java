package com.demonstration.demo.dto.account;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {
    private Long fromAccount;
    private Long toAccount;
    private BigDecimal amount;
}
