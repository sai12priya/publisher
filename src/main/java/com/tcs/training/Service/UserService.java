package com.tcs.training.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.training.Entity.User;
import com.tcs.training.Repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository urepo;

	public User authenticateAdmin(String username, String password) {
        User user = urepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        
        if (!"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("Access denied. Admin privileges required");
        }
        
        return user;
    }
	
	public User authenticateCustomer(String username, String password) {
	    // First check if user exists
	    User user = urepo.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    
	    // Then check password
	    if (!user.getPassword().equals(password)) {
	        throw new RuntimeException("Invalid password");
	    }
	    
	    // Finally check role
	    if (!"USER".equals(user.getRole())) {
	        throw new RuntimeException("Access denied. Customer account required");
	    }
	    
	    return user;
	}

	 public User registerUser(String username, String password, String confirmPassword) {
	        // Validate input
	        if (username == null || username.trim().isEmpty()) {
	            throw new IllegalArgumentException("Username cannot be empty");
	        }
	        
	        if (password == null || password.trim().isEmpty()) {
	            throw new IllegalArgumentException("Password cannot be empty");
	        }
	        
	        if (!password.equals(confirmPassword)) {
	            throw new IllegalArgumentException("Passwords do not match");
	        }
	        
	        if (urepo.findByUsername(username).isPresent()) {
	            throw new IllegalArgumentException("Username already exists");
	        }
	        
	        // Create and save user
	        User user = new User();
	        user.setUsername(username);
	        user.setPassword(password);
	        user.setRole("USER");
	        
	        return urepo.save(user);
	    }
		
	
}
