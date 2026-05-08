package com.elmifdali.digitalbanking.entities;

import com.elmifdali.digitalbanking.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Entity
@Data
@Inheritance(strategy =  InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE" ,length = 4)
public abstract class BankAccount {
    @Id
    private String id;
    private Double balance;
    private Date createdAt ;
    @Enumerated(EnumType.STRING)
private AccountStatus status;
@ManyToOne
private Customer customer ;
@OneToMany(mappedBy = "bankAccount")
private List<AccountOperation> accountOperations;
}
