package com.mindhub.homebanking.models;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void NumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertNotNull(cardNumber);
    }

    @Test
    public void cardCvvIsCreated(){
        long cardNumber = CardUtils.getCvvNumber();
        assertThat(String.valueOf(cardNumber),is(not(emptyOrNullString())));
    }

    @Test
    public void CvvIsCreated(){
        long cardNumber = CardUtils.getCvvNumber();
        assertTrue(cardNumber > 0);
    }

}
