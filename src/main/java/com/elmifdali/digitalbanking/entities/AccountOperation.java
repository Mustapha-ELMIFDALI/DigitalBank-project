package com.elmifdali.digitalbanking.entities;

import com.elmifdali.digitalbanking.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id ;
private Date operationsDate ;
private  double amount ;
@Enumerated(EnumType.STRING)
private OperationType type;
@ManyToOne
private BankAccount bankAccount;
private String description;
}
