package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImplement implements TransferService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public void saveTransfer(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }

    @Override
    public void deleteAccount(Account account) {
        transactionRepository.deleteByAccount(account);
    }
}
