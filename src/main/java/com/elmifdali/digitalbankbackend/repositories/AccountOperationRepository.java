package com.elmifdali.digitalbankbackend.repositories;

import com.elmifdali.digitalbankbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import com.elmifdali.digitalbankbackend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
    Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
