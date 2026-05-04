package com.elmifdali.digitalbankbackend.repositories;

import com.elmifdali.digitalbankbackend.entities.BankAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
