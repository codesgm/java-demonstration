package com.demonstration.demo.services.interfaces;

import java.math.BigDecimal;

public interface AccountService {

    boolean transfer(Long fromAccount, Long toAccount, BigDecimal amount);

}