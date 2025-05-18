package com.tcs.training.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.training.Entity.Product;
import com.tcs.training.Repository.ProductRepository;
@Service
public class ProductService {
	@Autowired
	ProductRepository repo;
	public Product addProduct(Product p) {
		System.out.println("im in publisher product service");
		System.out.println("");
		System.out.println(p);
		return repo.save(p);
	}
	public List<Product> findAllProducts(){
		return repo.findAll();
	}
	public Product findById(Long id) {
		return repo.findById(id).get();
	}
	
	public Product updateProduct(Product product) {
		return repo.save(product);
	}
	
	public int countProducts() {
		return repo.countTotalProducts();
	}
	
	public Product findProductId(Long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}
	
	public Integer getAvailableQuantity(Long productId) {
        return repo.findQuantityAvailableByProductId(productId);
    }
}
