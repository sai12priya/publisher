package com.tcs.training.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
    
    private Double price;
    @Column(name = "quantity" )
    private Integer quantityAvailable;
    
    
    @Column(name = "total_amount" )
    private Double totalAmount;
    
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    
    @Column(name = "expected_delivery_date")
    private LocalDate expectedDeliveryDate;
    
    public Purchase() {}

	public Purchase(Long id, Vendor vendor,Double price,Integer quantityAvailable,  Double totalAmount,
			LocalDate purchaseDate, LocalDate expectedDeliveryDate) {
		super();
		this.id = id;
		this.vendor = vendor;
		this.price=price;
		this.quantityAvailable=quantityAvailable;
		this.totalAmount = totalAmount;
		this.purchaseDate = purchaseDate;
		this.expectedDeliveryDate = expectedDeliveryDate;
		
	}


    public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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

	 
	@Override
	public String toString() {
		return "Purchase [id=" + id + ", vendor=" + vendor + ", totalAmount=" + totalAmount + ", purchaseDate=" + purchaseDate
				+ ", expectedDeliveryDate=" + expectedDeliveryDate + "]";
	}


}

