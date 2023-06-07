package com.suryahidayat.backend.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suryahidayat.backend.model.Transaction;
import com.suryahidayat.backend.service.TransactionService;

@Controller
@RequestMapping("/api/transaction/")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/")
    private ResponseEntity<List<Transaction>> getAllTransaction() {
        return new ResponseEntity<List<Transaction>>(transactionService.getAllTransaction(), HttpStatus.OK);
    }

    @PostMapping("/add")
    private ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/reset")
    private ResponseEntity<Void> resetTransaction() {
        transactionService.resetTransaction();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/{startDate}/{endDate}")
    private ResponseEntity<List<Transaction>> getTransactionByDateInterval(@PathVariable("startDate") Date startDate,
            @PathVariable("endDate") Date endDate) {
        return new ResponseEntity<List<Transaction>>(transactionService.getTransactionByDateInterval(startDate, endDate),
                HttpStatus.OK);
    }

}
