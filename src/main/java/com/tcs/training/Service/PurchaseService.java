package com.tcs.training.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tcs.training.Entity.Purchase;
import com.tcs.training.Entity.Vendor;
import com.tcs.training.Repository.PurchaseRepository;

@Service
public class PurchaseService {
	@Autowired
	PurchaseRepository prepo;
	 public Purchase addPurchase(Purchase purchase) {
	      
	    	System.out.println("im in publisher vendor service");
	        Purchase pur=prepo.save(purchase);
	        return pur;
	    }
	 public List<Vendor> findRecent8Vendors(){
	    	return prepo.findTop8ByOrderByPurchaseDateDesc();
	    }
	public List<Purchase> findAllPurchases() {
		// TODO Auto-generated method stub
		return prepo.findAll();
	}
	public Long getProductsPurchasedThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return prepo.countTotalProductsPurchasedInPeriod(startDate, endDate);
    }
    
    public Long getProductsPurchasedLastMonth() {
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(1).minusDays(1);
        return prepo.countTotalProductsPurchasedInPeriod(startDate, endDate);
    }
    
    public String getMonthlyComparison() {
        Long thisMonth = getProductsPurchasedThisMonth();
        Long lastMonth = getProductsPurchasedLastMonth();
        long difference = thisMonth - lastMonth;
        
        if (difference > 0) {
            return "+" + difference + " more than last month";
        } else if (difference < 0) {
            return difference + " less than last month";
        } else {
            return "same as last month";
        }
    }
    public List<Purchase> getRecentPurchases() {
        Pageable topEight = PageRequest.of(0, 8); // First page, 8 records
        return prepo.findTop8ByOrderByPurchaseDateDesc(topEight);
    }
    public Double getPurchasesThisMonth() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return prepo.getTotalAmountBetweenDates(startDate, endDate);
    }
    
    public Double getPurchasesLastMonth() {
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now().withDayOfMonth(1).minusDays(1);
        return prepo.getTotalAmountBetweenDates(startDate, endDate);
    }
    
    public String getMonthlyPurchaseComparison() {
        Double thisMonth = getPurchasesThisMonth();
        System.out.println(thisMonth);
        Double lastMonth = getPurchasesLastMonth();
        System.out.println(lastMonth);
       Double difference = thisMonth - lastMonth;
        
        if (difference > 0) {
            return "+" + difference + " more than last month";
        } else if (difference < 0) {
            return -difference + " less than last month";
        } else {
            return "same as last month";
        }
    }

}
