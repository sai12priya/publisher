package com.tcs.training.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.User;
import com.tcs.training.Entity.UserPurchase;

@Repository
public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Long> {
    List<UserPurchase> findByUser(User user);
    Optional<UserPurchase> findByInvoiceNumber(String invoiceNumber);
 
        UserPurchase findByIdAndUserId(Long id, Long userId);
    
}