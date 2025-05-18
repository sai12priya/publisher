package com.tcs.training.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.training.Entity.Purchase;
import com.tcs.training.Entity.Vendor;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
	@Query("select p.vendor from Purchase p order by p.purchaseDate DESC limit 8")
	List<Vendor> findTop8ByOrderByPurchaseDateDesc();

	@Query("SELECT COALESCE(SUM(p.quantityAvailable), 0) FROM Purchase p "
			+ "WHERE p.purchaseDate BETWEEN :startDate AND :endDate")
	Long countTotalProductsPurchasedInPeriod(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query("SELECT p FROM Purchase p " + "JOIN FETCH p.vendor v " + // Eagerly fetch vendor details
			"ORDER BY p.purchaseDate DESC")
	List<Purchase> findTop8ByOrderByPurchaseDateDesc(Pageable pageable);
	@Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM Purchase p WHERE p.purchaseDate BETWEEN :startDate AND :endDate")
	Double getTotalAmountBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
