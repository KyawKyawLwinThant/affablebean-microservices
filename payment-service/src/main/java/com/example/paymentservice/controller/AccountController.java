package com.example.paymentservice.controller;

import com.example.paymentservice.ds.Account;
import com.example.paymentservice.service.AccountService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class AccountController {
    @Autowired
    private AccountService accountService;
    record DepositRequest(String name,String email,double amount){
    }
    record DepositResponse(String name,double balance){}

    @PostMapping("/deposit")
    public DepositResponse deposit(@RequestBody DepositRequest request){
       Account account= accountService.deposit(request.name(),
                request.email(),request.amount());
       return new DepositResponse(account.getName(),
               accountService.getBalance(account.getEmail()));
    }
    record WithDrawRequest(String name,String email,double amount){}
    record WithDrawResponse(String name,double balance){}

    @PostMapping("/withdraw")
    public WithDrawResponse withdraw(@RequestBody WithDrawRequest request){
        Account account=accountService.withdraw(
                request.name(),
                request.email(),
                request.amount()
        );
        return new WithDrawResponse(account.getName(),
                accountService.getBalance(account.getEmail()));
    }
    record TransferRequest(
            @JsonProperty("from_name") String fromName,
            @JsonProperty("from_email") String fromEmail,
            @JsonProperty("to_name") String toName,
            @JsonProperty("to_email") String toEmail,
            double amount
    ){

    }
    record TransferResponse(
            @JsonProperty("from_name") String fromName,
            @JsonProperty("from_balance") double fromBalance,
            @JsonProperty("to_name") String toName,
            @JsonProperty("to_balance") double toBalance
    ){}
    @PostMapping("/transfer")
    public TransferResponse transfer(@RequestBody TransferRequest request){
        accountService.transfer(
                request.fromName(),
                request.fromEmail(),
                request.toName(),
                request.toEmail(),
                request.amount()
        );
        return new TransferResponse(
                request.fromName(),
                accountService.getBalance(request.fromEmail()),
                request.toName(),
                accountService.getBalance(request.toEmail())
        );
    }
}
