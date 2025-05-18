package com.tcs.training.dto;

import org.springframework.stereotype.Component;

import com.tcs.training.Entity.Product;
import com.tcs.training.Entity.Vendor;

@Component
public class VendorProductDTO {

	private Product product;
	private Vendor vendor;

	public VendorProductDTO() {
	}

	public VendorProductDTO(Product product, Vendor vendor) {
		super();
		this.product = product;
		this.vendor = vendor;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "VendorProductDTO [product=" + product + ", vendor=" + vendor + "]";
	}

}