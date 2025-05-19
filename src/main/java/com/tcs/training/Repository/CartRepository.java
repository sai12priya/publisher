package com.tcs.training.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
	Optional<Cart> findByUserId(int i);
	
}