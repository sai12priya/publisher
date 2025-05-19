package com.tcs.training.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByCart_UserId(int userId);

}