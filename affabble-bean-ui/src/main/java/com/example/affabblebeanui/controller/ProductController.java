package com.example.affabblebeanui.controller;

import com.example.affabblebeanui.ds.CartBean;
import com.example.affabblebeanui.ds.CustomerOrder;
import com.example.affabblebeanui.ds.TransPortInfoEntity;
import com.example.affabblebeanui.dto.Product;
import com.example.affabblebeanui.dto.ProductDto;
import com.example.affabblebeanui.service.ProductClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("webui")
public class ProductController {
    @Autowired
    private CartBean cartBean;

    @Autowired
    private ProductClientService productClientService;

    @GetMapping("/find-transport-info")
    public String findTransPortForm(){
        return "findTransPortForm";
    }
    @PostMapping("/find-transport-info")
    public String processFindTransPort(@RequestParam("email")String email,
                                       @RequestParam("password")String password){
        this.entity =productClientService.findTransPortInfo(email,password);
        return "redirect:/webui/trans-port-entity/view";
    }

    TransPortInfoEntity entity;


    @GetMapping("/trans-port-entity/view")
    public String transPortInfoView(Model model){
        model.addAttribute("customerName"
                ,entity.getCustomerName().get(0));
        model.addAttribute("products",entity.getProducts());
        model.addAttribute("email",entity.getEmail());
        model.addAttribute("totalAmount",
                entity.getCustomerOrders()
                        .stream()
                        .map(CustomerOrder::getTotalAmount)
                        .mapToDouble(d -> d).sum());
        return "transport-view";
    }


   @GetMapping("/checkout")
    public String checkoutForm(){
        return "checkout";

   }
   @PostMapping("/checkout")
   public String checkoutProcess(@RequestParam("name")String name,
                                  @RequestParam("email")String email,
                                  @ModelAttribute("updateTotalPrice")double total){
       System.out.println("Total=============="+ total);
        productClientService.checkout(name,email,total);
        return "redirect:/webui/home";
    }

    @GetMapping("/products")
    public String
    listProductByCategory(@RequestParam("category") String categoryName,
                          Model model) {
        model.addAttribute("products", productClientService
                .findProductByCategoryName(categoryName));
        this.categoryName = categoryName;
        return "products";
    }

    private String categoryName;

    @GetMapping("/product/add")
    public String addToCart(@RequestParam("id") int productId) {

        cartBean.addToCart(productClientService
                .findProductById(productId));

        return "redirect:/webui/products?category=" + categoryName;

    }

    @GetMapping("/cart-view")
    public String cartView(Model model) {
        model.addAttribute("productsInCart", cartBean.getCart());
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("success"
                ,model.containsAttribute("success"));
        return "cart-view";
    }
    List<Integer> itemList=new ArrayList<>();
    @PostMapping("/update-item")
    public String processItem(ProductDto productDto, RedirectAttributes attributes){
        itemList=productDto.getItemList();
        System.out.println("==================================="
                +itemList);

        int counter=0;

        Iterator<Product> itr=cartBean.getCart().iterator();
        while(itr.hasNext()){
            Product product=itr.next();
            product.setQuantity(itemList.get(counter));
            counter++;
        }

        attributes
                .addFlashAttribute("success",true);


        cartBean.getCart().forEach(System.out::println);

        return "redirect:/webui/cart-view";
    }
    @ModelAttribute("totalItem")
    public Integer totalItem(){
        return cartBean.getCart()
                .stream()
                .map( Product::getQuantity)
                .mapToInt(Integer::intValue)
                .sum();
    }
    @ModelAttribute("updateTotalPrice")
    public Double updateTotalPrice(){
        return cartBean.getCart()
                .stream()
                .map( p -> p.getPrice() * p.getQuantity())
                .mapToDouble(i -> i)
                .sum();
    }


    @ModelAttribute("total")
    public Double totalPrice() {
        return cartBean
                .getCart()
                .stream()
                .map(p -> p.getPrice() * 1)
                .mapToDouble(i -> i)
                .sum();
    }


    @ModelAttribute("cartSize")
    public Integer cartSize() {
        return cartBean.getCart().size();
    }


    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }
}
