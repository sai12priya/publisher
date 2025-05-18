package com.tcs.training.dto;

import java.time.LocalDate;

public class PurchaseDTO {
    private Long vendorId;
    private String productName;
    private int quantityAvailable;
    private double price;
    private double totalAmount;
    private LocalDate purchaseDate;
    private LocalDate expectedDeliveryDate;

    // Constructors, getters, and setters
    
    public PurchaseDTO() {}

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantity) {
        this.quantityAvailable = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double unitPrice) {
        this.price = unitPrice;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}