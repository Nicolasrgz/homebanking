package com.mindhub.homebanking;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Client {
    @Id
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    public Client (String first, String last){
        firstName = first;
        lastName = last;
    }

    public String getFirstName() {
        return firstName;
    }
}
