package com.elmifdali.digitalbankbackend.services;

import com.elmifdali.digitalbankbackend.dtos.*;
import com.elmifdali.digitalbankbackend.entities.BankAccount;
import com.elmifdali.digitalbankbackend.entities.CurrentAccount;
import com.elmifdali.digitalbankbackend.entities.Customer;
import com.elmifdali.digitalbankbackend.entities.SavingAccount;
import com.elmifdali.digitalbankbackend.exceptions.BalanceNotSufficientException;
import com.elmifdali.digitalbankbackend.exceptions.BankAccountNotFoundException;
import com.elmifdali.digitalbankbackend.exceptions.CustomerNotFoundException;

import java.util.List;
public interface BankAccountService {
     CustomerDTO saveCustomer(CustomerDTO customerDTO);
     CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
     SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
     List<CustomerDTO> listCustomers();
     BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
     void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
     void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

     List<BankAccountDTO> bankAccountList();

     CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

     CustomerDTO updateCustomer(CustomerDTO customerDTO);

     void deleteCustomer(Long customerId);

     List<AccountOperationDTO> accountHistory(String accountId);

     AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

     List<CustomerDTO> searchCustomers(String keyword);
}