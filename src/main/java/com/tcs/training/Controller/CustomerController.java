package com.tcs.training.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.training.Entity.User;
import com.tcs.training.Service.UserService;

@RestController
@RequestMapping("/user")
public class CustomerController {

	@Autowired
    private UserService userService;
    //@Autowired
    //private ProductService productService;
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            String confirmPassword = credentials.get("confirmpassword");
            
            User user = userService.registerUser(username, password, confirmPassword);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User registered successfully");
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
   
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            
            User user = userService.authenticateCustomer(username, password);
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Login successful");
            response.put("role", user.getRole());
            response.put("id", String.valueOf(user.getId()));
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            
            HttpStatus status = HttpStatus.BAD_REQUEST;
            
            if (e.getMessage().equals("User not found")) {
                status = HttpStatus.NOT_FOUND;
            } else if (e.getMessage().equals("Invalid password")) {
                status = HttpStatus.UNAUTHORIZED;
            } else if (e.getMessage().equals("Access denied. Customer account required")) {
                status = HttpStatus.FORBIDDEN;
            }
            
            return ResponseEntity.status(status).body(errorResponse);
        }
    }
//    @GetMapping("/dashboard")
//    public String showCustomerDashboard(Model model) {
//    	 try {
//    	        List<Product> products = productService.allProducts();
//    	        model.addAttribute("products", products);
//    	        return "userDashboard";
//    	    } catch (Exception e) {
//    	        model.addAttribute("error", "Error loading products");
//    	        return "error"; //error page
//    	    }
//    }
    
}
