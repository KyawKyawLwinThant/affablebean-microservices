package com.example.paymentservice.controller;

import com.example.paymentservice.ds.Customer;
import com.example.paymentservice.service.AccountService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//security api - register
/*
    register() - api -
 */
@Controller
@RequestMapping("/payment")
public class CustomerController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AccountService accountService;
    @GetMapping({"/","/home"})
    public String home(Model model){
        model.addAttribute("success",model
                .containsAttribute("success"));
        return "home";
    }
    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("customer",new Customer());
        model.addAttribute("success",
                model.containsAttribute("success"));
        return "customer-form";
    }
    @PostMapping("/register")
    public String register(Customer customer, RedirectAttributes redirectAttributes){
        accountService.register(customer);
        redirectAttributes.addFlashAttribute("success",
                true);
        return "redirect:/payment/register";
    }
    @GetMapping("/customer/deposit")
    public String depositForm(){
        return "deposit";
    }
    record LoginRequest(@JsonProperty("email") String email,
                        @JsonProperty("password")String password){

    }
    record Token(@JsonProperty("token") String token){}
    @PostMapping("/customer/deposit")
    public String saveDeposit(@RequestParam("name")String name,
                              @RequestParam("email")String email,
                              @RequestParam("password")String password,
                              @RequestParam("amount")double amount,
                              RedirectAttributes redirectAttributes){
        var login=new LoginRequest(email,password);
        ResponseEntity<Token> response=
                restTemplate
                        .postForEntity("http://localhost:8070/security/login"
                        ,login,Token.class);
        String authToken=response.getBody().token();

        HttpHeaders headers=new HttpHeaders();
        headers.add("Authorization"
                ,"Bearer "+ authToken);
        HttpEntity<String> httpEntity=new HttpEntity<>(headers);

        ResponseEntity<String> response1=restTemplate
                .exchange("http://localhost:8070/security/user",
                HttpMethod.GET,
                httpEntity,
                String.class);

        if(response1.getStatusCode().is2xxSuccessful()){
            accountService.deposit(name,email,amount);
            redirectAttributes.addFlashAttribute("success",
                    true);
        }

        return "redirect:/payment/home";
    }
}
