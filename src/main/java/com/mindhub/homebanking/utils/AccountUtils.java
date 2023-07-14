package com.mindhub.homebanking.utils;

public class AccountUtils {

    public AccountUtils() {
    }

    public static String getAccountNumber() {
        String accountNumber;
        accountNumber = "VIN-" + (long) ((Math.random() * (99999999 - 10000000)) + 10000000);
        return accountNumber;
    }

}
