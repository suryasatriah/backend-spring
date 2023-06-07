package com.suryahidayat.backend.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suryahidayat.backend.model.Transaction;
import com.suryahidayat.backend.repo.TransactionRepo;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    AccountService accountService;

    public List<Transaction> getAllTransaction() {
        return transactionRepo.findAll();
    }

    public Transaction getTransaction(Integer id) {
        return transactionRepo.findById(id).get();
    }

    public List<Transaction> getTransactionByDateInterval(Date startDate, Date endDate) {
        return transactionRepo.findByDateBetween(startDate, endDate);
    }

    public void addTransaction(Transaction transaction) {
        transactionRepo.save(transaction);

        // add/update account point
        Integer amount = transaction.getAmount();
        Integer accountId = transaction.account.getId();
        String description = transaction.getDescription();
        Integer newPoint = calculateTransactionPoint(accountId, amount, description);
        accountService.addPoint(accountId, newPoint);

    }

    public void resetTransaction() {
        transactionRepo.deleteAll();
    }


    public int calculateTransactionPoint(Integer accountId, Integer amount, String description) {
        Integer point = accountService.getAccount(accountId).getPoint();

        if (description.equalsIgnoreCase("beli pulsa") && amount > 10000) {
            if (amount <= 30000) {
                point += (amount - 10000) / 1000;
            } else if (amount > 30000) {
                point += (2 * ((amount - 30000) / 1000)) + 20;
            }
        } else if (description.equalsIgnoreCase("bayar listrik") && amount > 50000) {
            if (amount <= 100000) {
                point += (amount - 50000) / 2000;
            } else if (amount > 100000) {
                point = (2 * ((amount - 100000) / 2000)) + 25;
            }
        }

        return point;
    }
}
