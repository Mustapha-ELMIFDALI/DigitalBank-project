package com.elmifdali.digitalbanking.repositories;

import com.elmifdali.digitalbanking.entities.AccountOperation;
import com.elmifdali.digitalbanking.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
