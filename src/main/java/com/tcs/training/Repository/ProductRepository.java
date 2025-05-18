package com.tcs.training.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
	public Product findByName(String name);
	
	@Query("SELECT COUNT(p) FROM Product p")
    int countTotalProducts();

	@Query("SELECT p.quantityAvailable FROM Product p WHERE p.id = :productId")
    Integer findQuantityAvailableByProductId(@Param("productId") Long productId);
    
}
