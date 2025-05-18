package com.tcs.training.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.training.Entity.Product;
import com.tcs.training.Service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService pservice;

	@GetMapping("/allProducts")
	public ResponseEntity<?> allProducts() {
		try {
			List<Product> products = pservice.findAllProducts();
			return ResponseEntity.ok(products.isEmpty() ? Collections.emptyList() : products);
		} catch (Exception e) { // Consider specific exceptions like DataAccessException

			return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
		}
	}

	@GetMapping("/countProducts")
	public ResponseEntity<?> countAllProducts() {
		try {
			int count = pservice.countProducts();
			return ResponseEntity.ok(count);
		} catch (Exception e) { // Consider specific exceptions like DataAccessException

			return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
		}
	}

	@GetMapping("/byid")
	public ResponseEntity<?> productById(@RequestParam Long id) {
		try {
			Product p = pservice.findProductId(id);
			return ResponseEntity.ok(p);
		} catch (Exception e) { // Consider specific exceptions like DataAccessException

			return ResponseEntity.status(500).body(Map.of("error", "Internal server error"));
		}
	}

	@PostMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestBody Product updatedProduct, Model model) {
		 try {
	           
	          Product existingProduct  = pservice.findProductId(updatedProduct.getId());

	            // Update the fields
	            existingProduct.setName(updatedProduct.getName());
	            existingProduct.setDescription(updatedProduct.getDescription());
	            
	            // Save the updated product
	            Product savedProduct = pservice.updateProduct(existingProduct);

	            return ResponseEntity.ok(savedProduct);

	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(Map.of("error", "Failed to update product: " + e.getMessage()));
	        }
	    
	}
	
	@GetMapping("/{id}/availableQuantity")
    public ResponseEntity<Integer> getAvailableQuantity(@PathVariable("id") Long productId) {
        Integer quantity = pservice.getAvailableQuantity(productId);
        if (quantity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(quantity);
    }

//	@PostMapping("/add")
//	public String addProduct(Product p) {
//		ps.addProduct(p);
//		return "redirect:/admin/dashboard";
//	}
//	@GetMapping("admin/edit/{pid}")
//    public String showEditForm(@PathVariable int pid, Model model) {
//        Product product = ps.getProductById(pid);
//        model.addAttribute("product", product);
//        return "editproduct"; // Thymeleaf template for editing
//    }
//
//  
//    
//    @DeleteMapping("admin/delete/{pid}")
//    public String deleteProduct(@PathVariable int pid) {
//    	ps.deleteProduct(pid);
//    	return "redirect:/admin/dashboard";
//    }
//    

}
