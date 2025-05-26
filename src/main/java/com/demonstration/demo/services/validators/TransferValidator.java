package com.demonstration.demo.services.validators;


import com.demonstration.demo.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransferValidator {

    private final AccountRepository accountRepository;

    private static final BigDecimal MAX_TRANSFER_AMOUNT = new BigDecimal("999999999999.99");
    private static final BigDecimal MIN_TRANSFER_AMOUNT = new BigDecimal("0.01");
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    public void validateTransferData(Long fromAccount, Long toAccount, BigDecimal amount) {
        validateAccountNumbers(fromAccount, toAccount);
        validateTransferAmount(amount);
        validateAccountsExist(fromAccount, toAccount);
    }

    private void validateAccountNumbers(Long fromAccount, Long toAccount) {
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Números das contas não podem ser nulos");
        }

        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta");
        }

        if (fromAccount <= 0 || toAccount <= 0) {
            throw new IllegalArgumentException("Números das contas devem ser positivos");
        }
    }

    private void validateTransferAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Valor da transferência não pode ser nulo");
        }

        if (amount.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo");
        }

        if (amount.compareTo(MIN_TRANSFER_AMOUNT) < 0) {
            throw new IllegalArgumentException("Valor mínimo para transferência é R$ 0,01");
        }

        if (amount.compareTo(MAX_TRANSFER_AMOUNT) > 0) {
            throw new IllegalArgumentException("Valor muito grande para transferência");
        }

        if (amount.scale() > 2) {
            throw new IllegalArgumentException("Valor não pode ter mais de 2 casas decimais");
        }
    }

    private void validateAccountsExist(Long fromAccount, Long toAccount) {
        if (!accountRepository.existsByAccountNumber(fromAccount)) {
            throw new IllegalArgumentException("Conta origem não encontrada: " + fromAccount);
        }

        if (!accountRepository.existsByAccountNumber(toAccount)) {
            throw new IllegalArgumentException("Conta destino não encontrada: " + toAccount);
        }
    }
}