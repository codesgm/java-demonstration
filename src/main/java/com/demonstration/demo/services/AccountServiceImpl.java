package com.demonstration.demo.services;

import com.demonstration.demo.entities.Account;
import com.demonstration.demo.services.interfaces.AccountService;
import com.demonstration.demo.repositories.AccountRepository;
import com.demonstration.demo.services.validators.TransferValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransferValidator transferValidator;
    private final ConcurrentHashMap<Long, ReentrantLock> accountLocks = new ConcurrentHashMap<>(1000, 0.75f, 16);

    private static final long LOCK_TIMEOUT_SECONDS = 2;

    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 6)
    public boolean transfer(Long fromAccountNumber, Long toAccountNumber, BigDecimal amount) {

        transferValidator.validateTransferData(fromAccountNumber, toAccountNumber, amount);
        return executeWithOrderedLocks(fromAccountNumber, toAccountNumber, amount);
    }

    private boolean executeWithOrderedLocks(Long fromAccount, Long toAccount, BigDecimal amount) {

        Long firstLock = fromAccount < toAccount ? fromAccount : toAccount;
        Long secondLock = fromAccount < toAccount ? toAccount : fromAccount;

        ReentrantLock lock1 = getAccountLock(firstLock);
        ReentrantLock lock2 = getAccountLock(secondLock);

        boolean firstAcquired = false;
        boolean secondAcquired = false;

        try {
            firstAcquired = lock1.tryLock(LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!firstAcquired) return false;

            secondAcquired = lock2.tryLock(LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!secondAcquired) return false;

            return executeTransferWithCache(fromAccount, toAccount, amount);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (secondAcquired) lock2.unlock();
            if (firstAcquired) lock1.unlock();
        }
    }

    private boolean executeTransferWithCache(Long fromAccount, Long toAccount, BigDecimal amount) {

        Account source = getAccountForUpdate(fromAccount);
        Account destination = getAccountForUpdate(toAccount);

        if (source == null || destination == null) return false;
        if (source.getBalance().compareTo(amount) < 0) return false;

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));

        accountRepository.saveAll(Arrays.asList(source, destination));
        return true;
    }

    private Account getAccountForUpdate(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElse(null);
    }

    private ReentrantLock getAccountLock(Long accountNumber) {
        return accountLocks.computeIfAbsent(accountNumber, k -> new ReentrantLock(true));
    }

}
