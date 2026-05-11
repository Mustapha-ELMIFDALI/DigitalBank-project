package com.elmifdali.digitalbanking.exeptions;

public class BalanceNotSufficienExeption extends Exception {
    public BalanceNotSufficienExeption(String bankAccountNotFoundExeption) {
        super(bankAccountNotFoundExeption);
    }
}
