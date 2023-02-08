package com.example.affabblebeanui.controller;

import com.example.affabblebeanui.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("webui")
public class ProductController {
    @Autowired
    private ProductClientService productClientService;
    @GetMapping("/products")
    public String
    listProductByCategory(@RequestParam("category")String categoryName,
    Model model){
        model.addAttribute("products",productClientService
                .findProductByCategoryName(categoryName));
        return "products";
    }


    @GetMapping({"/","/home"})
    public String home(){
        return "home";
    }
}
