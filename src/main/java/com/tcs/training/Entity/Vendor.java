package com.tcs.training.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;
    @Column(name = "company_name")
    private String companyName;
    @Column( unique = true)
    private String email;
    private String phone;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
   
    private String address;
    private String status;
    
    
    

	public Vendor() {
		super();
	}

	public Vendor(Long id, String vendorName, String companyName, String email, String phone, Product product,
			String address, String status) {
		super();
		this.id = id;
		this.vendorName = vendorName;
		this.companyName = companyName;
		this.email = email;
		this.phone = phone;
		this.product = product;
		this.address = address;
		this.status = status;
	}
	public Vendor(String vendorName, String companyName, String email, String phone, Product product,
			String address, String status) {
		super();
		
		this.vendorName = vendorName;
		this.companyName = companyName;
		this.email = email;
		this.phone = phone;
		this.product = product;
		this.address = address;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", vendorName=" + vendorName + ", companyName=" + companyName + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", status=" + status + "]";
	}
    
	
}

