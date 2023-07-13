package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;

public class CardUtils {

        @Autowired
        private static CardService cardService;
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

