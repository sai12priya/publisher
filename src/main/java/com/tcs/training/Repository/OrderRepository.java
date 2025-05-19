package com.tcs.training.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.training.Entity.UserPurchase;
public interface OrderRepository extends JpaRepository<UserPurchase, Long> {
    Optional<UserPurchase> findByInvoiceNumber(String invoiceNumber);
    List<UserPurchase> findByUserId(Long userId);
}