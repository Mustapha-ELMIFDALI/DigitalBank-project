package com.elmifdali.digitalbanking.web;

import com.elmifdali.digitalbanking.dtos.CustomerDTO;
import com.elmifdali.digitalbanking.entities.BankAccount;
import com.elmifdali.digitalbanking.entities.Customer;
import com.elmifdali.digitalbanking.exeptions.CustomerNotFoundExeption;
import com.elmifdali.digitalbanking.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@Slf4j
public class CustomerRestController {
public BankAccountService bankAccountService ;
@GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.listCustomers();
    }
@GetMapping("/customers/{id}")
    public  CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundExeption {
    return bankAccountService.getCustomer(customerId);
    }
@PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
    return  bankAccountService.saveCustomer(customerDTO);
}

@PutMapping("/cutomers/{id}")
    public CustomerDTO updateCustomer(@PathVariable(name = "id") Long id,@RequestBody CustomerDTO customerDTO) {
customerDTO.setId(id);
return bankAccountService.updateCustomer(customerDTO);
}

@DeleteMapping("/cutomers/{id}")
    public void deleteCutomers(@PathVariable(name = "id") Long id){
    bankAccountService.deleteCustomer(id);
}


}
