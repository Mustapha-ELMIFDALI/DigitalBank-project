package com.elmifdali.digitalbanking.repositories;

import com.elmifdali.digitalbanking.entities.AccountOperation;
import com.elmifdali.digitalbanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
