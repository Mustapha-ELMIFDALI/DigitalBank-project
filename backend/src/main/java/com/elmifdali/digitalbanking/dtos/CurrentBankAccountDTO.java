package com.elmifdali.digitalbanking.dtos;
import com.elmifdali.digitalbanking.enums.AccountStatus;
import lombok.Data;
import java.util.Date;

@Data
public class CurrentBankAccountDTO extends BankAccountDTO {
    private String id;
    private Double balance;
    private Date createdAt ;
    private CustomerDTO customerDTO;
    private AccountStatus status;
    private double overdraft;


}
