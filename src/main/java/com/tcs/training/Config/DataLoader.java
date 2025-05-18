package com.tcs.training.Config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tcs.training.Entity.User;
import com.tcs.training.Repository.UserRepository;

@Configuration
public class DataLoader {
    
    @Bean
    public CommandLineRunner initUsers(UserRepository repo) {
        return args -> {
            if (repo.findByUsername("admin").isEmpty()) {
                User admin = new User("admin", "admin123", "ADMIN");
                repo.save(admin);
            }
        };
    }
}