package com.elmifdali.digitalbankbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.elmifdali.digitalbankbackend.entities.BankAccount;
import com.elmifdali.digitalbankbackend.enums.OperationType;


import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
