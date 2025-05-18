package com.tcs.training.Controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.training.Entity.User;
import com.tcs.training.Service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    
    
    @GetMapping("/login")
    public String showAdminLoginPage(Model model) {
    	 model.addAttribute("error", null);
        return "adminlogin";
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> processAdminLogin(@RequestBody User user, Model model) {
        try {
            User authenticatedUser = userService.authenticateAdmin(user.getUsername(), user.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    
   // @GetMapping("/dashboard")
//    public String showAdminDashboard(Model model) {
//    	 try {
//    	        //List<Product> products = productService.allProducts();
//    	        model.addAttribute("products", products);
//    	        return "adminDashboard";
//    	    } catch (Exception e) {
//    	        model.addAttribute("error", "Error loading products");
//    	        return "error"; //error page
//    	    }
//    }
}