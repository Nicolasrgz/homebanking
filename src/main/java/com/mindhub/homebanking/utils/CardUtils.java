package com.mindhub.homebanking.utils;

public class CardUtils {
    private CardUtils() {}

    public static long getCvvNumber() {
        return (long) ((Math.random() * (999 - 100)) + 100);
    }

    public static String getCardNumber() {
        String cardNumber;
        cardNumber = String.format("%04d-%04d-%04d-%04d", (int)(Math.random()*10000),
                  (int)(Math.random()*10000),
                  (int)(Math.random()*10000),
                  (int)(Math.random()*10000));
        return cardNumber;
    }
}

