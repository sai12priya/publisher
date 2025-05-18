package com.tcs.training.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_name",nullable = false, unique = true)
    private String name;
    
    private String description;
   
    private Double price;
   
    private Integer quantityAvailable;
    
    @OneToOne(mappedBy = "product")
    private Vendor vendor;
    
    public Product() {}
    
    

	public Product(Long id, String productName, String description, Double price, Integer quantityAvailable,
			Vendor vendor) {
		super();
		this.id = id;
		this.name = productName;
		this.description = description;
		this.price = price;
		this.quantityAvailable = quantityAvailable;
		this.vendor = vendor;
	}
	public Product(String productName, String description, Double price, Integer quantityAvailable) {
		super();
//		this.id = id;
		this.name = productName;
		this.description = description;
		this.price = price;
		this.quantityAvailable = quantityAvailable;
//		this.vendor = vendor;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String productName) {
		this.name = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

//	public Vendor getVendor() {
//		return vendor;
//	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + name + ", description=" + description + ", price="
				+ price + ", quantityAvailable=" + quantityAvailable  + "]";
	}
    
    
    
 
}