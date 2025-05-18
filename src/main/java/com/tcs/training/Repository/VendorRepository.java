package com.tcs.training.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
	boolean existsByEmail(String email);

	@Query("SELECT COUNT(v) FROM Vendor v")
	int countTotalVendors();

	@Query("SELECT COUNT(v) FROM Vendor v WHERE v.status = 'active'")
	int countActiveVendors();
}
