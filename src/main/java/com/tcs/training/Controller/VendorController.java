package com.tcs.training.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.training.Entity.Product;
import com.tcs.training.Entity.Vendor;
import com.tcs.training.Service.ProductService;
import com.tcs.training.Service.VendorService;
import com.tcs.training.dto.VendorProductDTO;



@RestController
@RequestMapping("/vendor")
public class VendorController {

	@Autowired
	VendorService vservice;
	@Autowired
	ProductService pservice;

	@PostMapping("/add")
	public ResponseEntity<?> addVendor(@RequestBody VendorProductDTO vpdto) {
		
		try {
            System.out.println("Received DTO: " + vpdto);
            if (vpdto == null || vpdto.getVendor() == null || vpdto.getProduct() == null) {
                return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid DTO: Vendor or Product is null"));
            }

            Product product = vpdto.getProduct();
            Vendor vendor = vpdto.getVendor();
            System.out.println("Product: " + product);
            System.out.println("Vendor: " + vendor);

            Product savedProduct = pservice.addProduct(product);
            vendor.setProduct(savedProduct);
            Vendor savedVendor = vservice.addVendor(vendor);
            System.out.println("Saved Vendor: " + savedVendor);
          return ResponseEntity.ok(savedVendor);
        } catch (Exception e) {
            System.err.println("Error in addVendor: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
        }
    
    }
	@GetMapping("/allVendors")
	public ResponseEntity<?> allVendors(){
	
		try {
            List<Vendor> vendors = vservice.allVendors();
            return ResponseEntity.ok(vendors.isEmpty() ? Collections.emptyList() : vendors);
        } catch (Exception e) { // Consider specific exceptions like DataAccessException
            
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Internal server error"));
        }
    
	}
	@GetMapping("/countVendors")
	public ResponseEntity<?> countAllVendors(){
		try {
            int count = vservice.countVendors();
            return ResponseEntity.ok(count);
        } catch (Exception e) { // Consider specific exceptions like DataAccessException
            
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Internal server error"));
        }
	}
	@GetMapping("/countActive")
	public ResponseEntity<?> countActiveVendors(){
		try {
            int count = vservice.countActiveVendors();
           return ResponseEntity.ok(count);
        } catch (Exception e) { // Consider specific exceptions like DataAccessException
            
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Internal server error"));
        }
		
	}
	@GetMapping("/byid")
	public ResponseEntity<?> vendorById(@RequestParam Long id){
		try {
			Vendor v = vservice.getVendorById(id);
			return ResponseEntity.ok(v);
		} catch (Exception e) { // Consider specific exceptions like DataAccessException

			return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
		}
	}
	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody Vendor updatedVendor, Model model) {
		 try {
	           
	         Vendor existingVendor  = vservice.getVendorById(updatedVendor.getId());

	            // Update the fields
	            existingVendor.setVendorName(updatedVendor.getVendorName());
	            existingVendor.setCompanyName(updatedVendor.getCompanyName());
	            existingVendor.setAddress(updatedVendor.getAddress());
	            existingVendor.setEmail(updatedVendor.getEmail());
	            existingVendor.setPhone(updatedVendor.getPhone());
	            existingVendor.setStatus(updatedVendor.getStatus());
	            
	            // Save the updated product
	            Vendor savedVendor = vservice.updateVendor(existingVendor);

	            return ResponseEntity.ok(savedVendor);

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("error", "Failed to update product: " + e.getMessage()));
	        }
	    
	}

	
}
