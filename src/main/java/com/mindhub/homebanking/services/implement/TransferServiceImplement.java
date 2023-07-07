package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImplement implements TransferService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void saveTransfer(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
