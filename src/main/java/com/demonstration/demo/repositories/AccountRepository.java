package com.demonstration.demo.repositories;

import com.demonstration.demo.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);

    boolean existsByAccountNumber(Long accountNumber);

}

