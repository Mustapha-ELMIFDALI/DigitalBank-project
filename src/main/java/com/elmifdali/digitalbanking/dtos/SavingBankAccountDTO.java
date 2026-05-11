package com.elmifdali.digitalbanking.dtos;
import com.elmifdali.digitalbanking.enums.AccountStatus;
import lombok.Data;
import java.util.Date;

@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String id;
    private Double balance;
    private Date createdAt ;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;


}
