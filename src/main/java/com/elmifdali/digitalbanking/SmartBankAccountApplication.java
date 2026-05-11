package com.elmifdali.digitalbanking;

import com.elmifdali.digitalbanking.entities.*;
import com.elmifdali.digitalbanking.enums.AccountStatus;
import com.elmifdali.digitalbanking.enums.OperationType;
import com.elmifdali.digitalbanking.repositories.AccountOperationRepository;
import com.elmifdali.digitalbanking.repositories.BankAccountRepository;
import com.elmifdali.digitalbanking.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class SmartBankAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBankAccountApplication.class, args);
    }
@Bean
    CommandLineRunner start(
            CustomerRepository customerRepository,
            BankAccountRepository bankAccountRepository,
            AccountOperationRepository accountOperationRepository
){
    return args -> {

        Stream.of("hassan","Ilyas","sdek").forEach(name->{
            Customer customer =new Customer();
            customer.setName(name);
            customer.setEmail(name+"@proton.com");
            customerRepository.save(customer);
        });

        customerRepository.findAll().forEach(e ->{
            CurrentAccount currentAccount=new CurrentAccount();
            currentAccount.setBalance(Math.random()*9000);
            currentAccount.setId(UUID.randomUUID().toString());
            currentAccount.setCreatedAt(new Date());
            currentAccount.setStatus(AccountStatus.CREATED);
            currentAccount.setCustomer(e);
            currentAccount.setOverDraft(9000);
            bankAccountRepository.save(currentAccount);
            SavingAccount savingAccount=new SavingAccount();
            savingAccount.setBalance(Math.random()*9000);
            savingAccount.setCreatedAt(new Date());
            savingAccount.setId(UUID.randomUUID().toString());
            savingAccount.setStatus(AccountStatus.CREATED);
            savingAccount.setCustomer(e);
            savingAccount.setInterestRate(5.6);
            bankAccountRepository.save(savingAccount);




        });

        bankAccountRepository.findAll().forEach(acc->{
            for (int i =0 ;i<5 ; i++){
                AccountOperation accountOperation = new AccountOperation();
                accountOperation.setAmount(Math.random()*12000);
                accountOperation.setOperationsDate(new Date());
                accountOperation.setType(Math.random()>0.5 ? OperationType.DEBIT : OperationType.CREDIT);
                accountOperation.setBankAccount(acc);
                accountOperationRepository.save(accountOperation);
            }
        });
    };
}




}
