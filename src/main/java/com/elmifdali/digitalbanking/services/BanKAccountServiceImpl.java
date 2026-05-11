package com.elmifdali.digitalbanking.services;

import com.elmifdali.digitalbanking.dtos.AccountHistoryDTO;
import com.elmifdali.digitalbanking.dtos.AccountOperationDTO;
import com.elmifdali.digitalbanking.dtos.BankAccountDTO;
import com.elmifdali.digitalbanking.dtos.CustomerDTO;
import com.elmifdali.digitalbanking.entities.*;

import com.elmifdali.digitalbanking.enums.OperationType;
import com.elmifdali.digitalbanking.exeptions.BalanceNotSufficienExeption;
import com.elmifdali.digitalbanking.exeptions.BankAccountNotFoundException;
import com.elmifdali.digitalbanking.exeptions.CustomerNotFoundExeption;
import com.elmifdali.digitalbanking.mappers.BankAccountMapperImpl;
import com.elmifdali.digitalbanking.repositories.AccountOperationRepository;
import com.elmifdali.digitalbanking.repositories.BankAccountRepository;
import com.elmifdali.digitalbanking.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BanKAccountServiceImpl implements BankAccountService{

private CustomerRepository customerRepository ;
private BankAccountRepository bankAccountRepository ;
private AccountOperationRepository accountOperationRepository ;
private BankAccountMapperImpl dtoMapper ;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return  dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initilalBalance, Long cutomerId, Long overDraft) throws CustomerNotFoundExeption {
        Customer customer = customerRepository.findById(cutomerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundExeption("customer not found");
        }
        CurrentAccount bankAccount ;
        bankAccount = new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initilalBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setOverDraft(overDraft);
        bankAccountRepository.save(bankAccount);
        return  bankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initilalBalance, String type, Long cutomerId, double intersetRate) throws CustomerNotFoundExeption {
        Customer customer = customerRepository.findById(cutomerId).orElse(null);
        if (customer==null){
            throw new CustomerNotFoundExeption("customer not found");
        }
        SavingAccount bankAccount ;
       bankAccount = new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setCreatedAt(new Date());
        bankAccount.setBalance(initilalBalance);
        bankAccount.setCustomer(customer);
        bankAccount.setInterestRate(intersetRate);
   bankAccountRepository.save(bankAccount);
  return  bankAccount;
    }

    @Override
    public List<CustomerDTO> listCustomers() {

List<Customer> customers = customerRepository.findAll();
List<CustomerDTO> collect =  customers.stream().map(e->dtoMapper.fromCustomer(e)).collect(Collectors.toList());
      return collect;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }
    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficienExeption {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount.getBalance()<amount){
            throw  new BalanceNotSufficienExeption("balance not enough");
        }
AccountOperation accountOperation =new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationsDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationsDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficienExeption {
debit(accountIdSource,amount,"transfer");
 credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }
@Override
public CustomerDTO getCustomer(Long customerId) {
       Customer customer= customerRepository.findById(customerId).orElseThrow(()-> {new CustomerNotFoundExeption("customer not found");
           return null;
       });
        return  dtoMapper.fromCustomer(customer);
}

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return  dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }

    @Override
public void deleteCustomer(Long cutomerId){
        customerRepository.deleteById(cutomerId);
    }



    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountIdOrderByOperationsDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }


}
