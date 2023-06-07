package com.suryahidayat.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suryahidayat.backend.model.Account;
import com.suryahidayat.backend.service.AccountService;

@RestController
@RequestMapping("/api/account/")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    private ResponseEntity<List<Account>> getAllAccount() {
        return new ResponseEntity<List<Account>>(accountService.getAllAccount(), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<Void> addAccount(@RequestBody Account account) {
        accountService.addAccount(account);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/reset")
    private ResponseEntity<Void> resetAccount() {
        accountService.resetAccount();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
