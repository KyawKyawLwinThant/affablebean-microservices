package com.example.paymentservice.dao;

import com.example.paymentservice.ds.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account,Integer> {
    Optional<Account> findAccountByEmail(String email);
    @Query("select a.amount from Account  a where a.email =:email")
    Optional<Double> getBalance(@Param("email") String email);
}
