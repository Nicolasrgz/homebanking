package com.mindhub.homebanking.models;

import com.mindhub.homebanking.utils.AccountUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class AccountUtilsTests {

    @Test
    public void accountNumberCreated(){
        String accountNumber = AccountUtils.getAccountNumber();
        assertThat(accountNumber,is(not(emptyOrNullString())));
    }

}
