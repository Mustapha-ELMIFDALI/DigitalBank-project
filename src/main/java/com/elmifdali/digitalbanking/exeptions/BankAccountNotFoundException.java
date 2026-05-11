package com.elmifdali.digitalbanking.exeptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String bankAccountNotFoundExeption) {
        super(bankAccountNotFoundExeption);
    }
}
