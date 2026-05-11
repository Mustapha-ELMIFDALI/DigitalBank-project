package com.elmifdali.digitalbanking.services;

import com.elmifdali.digitalbanking.dtos.AccountHistoryDTO;
import com.elmifdali.digitalbanking.dtos.AccountOperationDTO;
import com.elmifdali.digitalbanking.dtos.BankAccountDTO;
import com.elmifdali.digitalbanking.dtos.CustomerDTO;
import com.elmifdali.digitalbanking.entities.BankAccount;
import com.elmifdali.digitalbanking.entities.CurrentAccount;
import com.elmifdali.digitalbanking.entities.Customer;
import com.elmifdali.digitalbanking.entities.SavingAccount;
import com.elmifdali.digitalbanking.exeptions.BalanceNotSufficienExeption;
import com.elmifdali.digitalbanking.exeptions.BalanceNotSufficientException;
import com.elmifdali.digitalbanking.exeptions.BankAccountNotFoundException;
import com.elmifdali.digitalbanking.exeptions.CustomerNotFoundExeption;

import java.util.List;

public interface BankAccountService {
     CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentAccount saveCurrentBankAccount(double initilalBalance, Long cutomerId, Long overDraft) throws CustomerNotFoundExeption;
    SavingAccount saveSavingBankAccount(double initilalBalance, String type , Long cutomerId, double intersetRate) throws CustomerNotFoundExeption;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    void debit(String accountId, double amount , String description) throws BankAccountNotFoundException, BalanceNotSufficienExeption;
    void credit(String accountId,double amount ,String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource,String accountIdDestination,double amount ) throws BankAccountNotFoundException, BalanceNotSufficienExeption;

    CustomerDTO getCustomer(Long customerId);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    List<BankAccountDTO> bankAccountList();
    void deleteCustomer(Long cutomerId);


     List<AccountOperationDTO> accountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;


}
