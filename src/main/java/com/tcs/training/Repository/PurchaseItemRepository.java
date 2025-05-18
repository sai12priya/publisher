package com.tcs.training.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.PurchaseItem;
import com.tcs.training.Entity.UserPurchase;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
    List<PurchaseItem> findByUserPurchase(UserPurchase userPurchase);
    List<PurchaseItem> findByUserPurchaseId(Long purchaseId);
}