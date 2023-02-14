package com.example.paymentservice.service;


import com.example.paymentservice.controller.AccountController;
import com.example.paymentservice.dao.AccountDao;
import com.example.paymentservice.ds.Account;
import com.example.paymentservice.ds.Customer;
import com.example.paymentservice.exception.AccountNotFoundException;
import com.example.paymentservice.exception.InsufficientAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RestTemplate restTemplate;


    public Account deposit(String name, String email, double amount) {
        Optional<Account> accountOptional=accountDao
                .findAccountByEmail(email);
        if(accountOptional.isPresent()){
            double balance=getBalance(email);
            double updateAmount=balance+amount;
            Account account=accountOptional.get();
            account.setAmount(updateAmount);
            account.setUpdateTime(LocalDateTime.now());
            account.setName(account.getName());
            return accountDao.saveAndFlush(account);
        }
        else{
            Account account=new Account(name,email,amount
                    ,LocalDateTime.now());
            return accountDao.save(account);
        }
    }

    public double getBalance(String email){
        return accountDao.getBalance(email)
                .orElseThrow(AccountNotFoundException::new);
    }

    public Account withdraw(String name, String email, double amount) {
        Account account=accountDao.findAccountByEmail(email)
                .orElseThrow(AccountNotFoundException::new);
        if(amount > account.getAmount()){
            throw new InsufficientAmountException();
        }
        double updateAmount= account.getAmount() - amount;
        account.setAmount(updateAmount);
        account.setEmail(account.getEmail());
        account.setName(account.getName());
        account.setUpdateTime(LocalDateTime.now());
        return accountDao.saveAndFlush(account);
    }
    @Transactional
    public void transfer(String fromName, String fromEmail, String toName, String toEmail, double amount) {
        withdraw(fromName,fromEmail,amount);
        deposit(toName,toEmail,amount);
    }

    public void register(Customer customer) {
        try{
            restTemplate.postForEntity("http://localhost:8070/security/register"
                    ,customer,String.class);
        }catch (Exception e){
            System.out.println(e);
        }

    }
}
