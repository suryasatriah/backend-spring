package com.suryahidayat.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suryahidayat.backend.model.Account;
import com.suryahidayat.backend.repo.AccountRepo;

@Service
@Transactional
public class AccountService {

    @Autowired
    AccountRepo accountRepo;

    public List<Account> getAllAccount() {
        return (List<Account>) accountRepo.findAll();
    }

    public Account getAccount(Integer id) {
        return accountRepo.findById(id).get();
    }

    public void addAccount(Account account) {
        accountRepo.save(account);
    }

    public void addPoint(Integer id, Integer point) {
        getAccount(id).setPoint(point);
    }

    public void resetAccount() {
        accountRepo.deleteAll();
    }
}
