package com.suryahidayat.backend.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suryahidayat.backend.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByDateBetween(Date startDate, Date endDate);
}
