package com.tcs.training.Entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//src/main/java/com/yourpackage/model/PurchaseItem.java
@Entity
@Table(name = "purchase_items")
public class PurchaseItem {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_purchase_id")
 private UserPurchase userPurchase;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "product_id")
 private Product product;

 private int quantity;

 @Column(name = "unit_price", precision = 10, scale = 2)
 private BigDecimal unitPrice;
 

public PurchaseItem() {
	super();
}

public PurchaseItem(Long id, UserPurchase purchase, Product product, int quantity, BigDecimal unitPrice) {
	super();
	this.id = id;
	this.userPurchase = purchase;
	this.product = product;
	this.quantity = quantity;
	this.unitPrice = unitPrice;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public UserPurchase getUserPurchase() {
	return userPurchase;
}

public void setUserPurchase(UserPurchase purchase) {
	this.userPurchase = purchase;
}

public Product getProduct() {
	return product;
}

public void setProduct(Product product) {
	this.product = product;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public BigDecimal getUnitPrice() {
	return unitPrice;
}

public void setUnitPrice(BigDecimal unitPrice) {
	this.unitPrice = unitPrice;
}

@Override
public String toString() {
	return "PurchaseItem [id=" + id + ", user_purchase=" + userPurchase + ", product=" + product + ", quantity=" + quantity
			+ ", unitPrice=" + unitPrice + "]";
}

 // Getters and setters
 
}