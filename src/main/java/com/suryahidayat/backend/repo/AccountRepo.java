package com.suryahidayat.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suryahidayat.backend.model.Account;

public interface AccountRepo extends JpaRepository<Account, Integer> {

}
