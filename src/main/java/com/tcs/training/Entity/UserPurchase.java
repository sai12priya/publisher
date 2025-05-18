package com.tcs.training.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
//src/main/java/com/yourpackage/model/Purchase.java
@Entity
@Table(name = "userpurchases")
public class UserPurchase {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "invoice_number", unique = true)
 private String invoiceNumber;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_id")
 private User user;

 @OneToMany(
     mappedBy = "userPurchase",
     cascade = CascadeType.ALL,
     orphanRemoval = true
 )
 private List<PurchaseItem> items = new ArrayList<>();

 @Enumerated(EnumType.STRING)
 private OrderStatus status = OrderStatus.PENDING;

 @Column(precision = 10, scale = 2)
 private BigDecimal subtotal;

 @Column(precision = 10, scale = 2)
 private BigDecimal shipping;

 @Column(precision = 10, scale = 2)
 private BigDecimal tax;

 @Column(precision = 10, scale = 2)
 private BigDecimal total;

 private String paymentMethod;

 @Embedded
 private ShippingAddress shippingAddress;

 @Column(name = "purchase_date")
 private LocalDateTime purchaseDate = LocalDateTime.now();
 
 

public UserPurchase() {
	super();
}

public UserPurchase(Long id, String invoiceNumber, User user, List<PurchaseItem> items, OrderStatus status,
		BigDecimal subtotal, BigDecimal shipping, BigDecimal tax, BigDecimal total, String paymentMethod,
		ShippingAddress shippingAddress, LocalDateTime purchaseDate) {
	super();
	this.id = id;
	this.invoiceNumber = invoiceNumber;
	this.user = user;
	this.items = items;
	this.status = status;
	this.subtotal = subtotal;
	this.shipping = shipping;
	this.tax = tax;
	this.total = total;
	this.paymentMethod = paymentMethod;
	this.shippingAddress = shippingAddress;
	this.purchaseDate = purchaseDate;
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getInvoiceNumber() {
	return invoiceNumber;
}

public void setInvoiceNumber(String invoiceNumber) {
	this.invoiceNumber = invoiceNumber;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public List<PurchaseItem> getItems() {
	return items;
}

public void setItems(List<PurchaseItem> items) {
	this.items = items;
}

public OrderStatus getStatus() {
	return status;
}

public void setStatus(OrderStatus status) {
	this.status = status;
}

public BigDecimal getSubtotal() {
	return subtotal;
}

public void setSubtotal(BigDecimal subtotal) {
	this.subtotal = subtotal;
}

public BigDecimal getShipping() {
	return shipping;
}

public void setShipping(BigDecimal shipping) {
	this.shipping = shipping;
}

public BigDecimal getTax() {
	return tax;
}

public void setTax(BigDecimal tax) {
	this.tax = tax;
}

public BigDecimal getTotal() {
	return total;
}

public void setTotal(BigDecimal total) {
	this.total = total;
}

public String getPaymentMethod() {
	return paymentMethod;
}

public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
}

public ShippingAddress getShippingAddress() {
	return shippingAddress;
}

public void setShippingAddress(ShippingAddress shippingAddress) {
	this.shippingAddress = shippingAddress;
}

public LocalDateTime getPurchaseDate() {
	return purchaseDate;
}

public void setPurchaseDate(LocalDateTime purchaseDate) {
	this.purchaseDate = purchaseDate;
}

@Override
public String toString() {
	return "UserPurchase [id=" + id + ", invoiceNumber=" + invoiceNumber + ", user=" + user + ", items=" + items
			+ ", status=" + status + ", subtotal=" + subtotal + ", shipping=" + shipping + ", tax=" + tax + ", total="
			+ total + ", paymentMethod=" + paymentMethod + ", shippingAddress=" + shippingAddress + ", purchaseDate="
			+ purchaseDate + "]";
}


 // Generate getters and setters
}