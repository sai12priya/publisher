package com.tcs.training.Entity;

import jakarta.persistence.Embeddable;

//src/main/java/com/yourpackage/model/ShippingAddress.java
@Embeddable
public class ShippingAddress {
 private String street;
 private String apartment;
 private String city;
 private String state;
 private String country;
private String address;
private String address2;
private String postalCode;
private String phone;
public String getPhone() {
	return phone;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public String getApartment() {
	return apartment;
}
public void setApartment(String apartment) {
	this.apartment = apartment;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}

public String getCountry() {
	return country;
}
public void setCountry(String country) {
	this.country = country;
}
public void setAddress(String address) {
	// TODO Auto-generated method stub
	this.address=address;;
	
}
public String getAddress() {
	return address;
}
public void setAddress2(String address2) {
	// TODO Auto-generated method stub
	this.address2=address2;
	
}
public void setPostalCode(String postal) {
	// TODO Auto-generated method stub
	this.postalCode=postal;
	
}
public String getAddress2() {
	return address2;
}
public String getPostalCode() {
	return postalCode;
}
public void setPhone(String phone) {
	// TODO Auto-generated method stub
	this.phone=phone;
	
}

 // Generate getters and setters
 // Optional: Add validation annotations like @NotBlank
 
 
}