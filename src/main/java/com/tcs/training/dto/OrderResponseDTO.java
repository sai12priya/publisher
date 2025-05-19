package com.tcs.training.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.tcs.training.Entity.OrderStatus;

public class OrderResponseDTO {
    private String invoiceNumber;
    private LocalDateTime purchaseDate;
    private BigDecimal total;
    private OrderStatus status;
    private List<PurchaseItemDTO> items;
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public List<PurchaseItemDTO> getItems() {
		return items;
	}
	public void setItems(List<PurchaseItemDTO> items) {
		this.items = items;
	}

    // Getters and setters
    
}