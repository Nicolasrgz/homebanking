package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

public interface TransferService {

    void saveTransfer(Transaction transaction);
}
