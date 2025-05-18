package com.tcs.training.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.training.Entity.Vendor;
import com.tcs.training.Repository.ProductRepository;
import com.tcs.training.Repository.VendorRepository;


@Service
public class VendorService {
	@Autowired
	VendorRepository vrepo;
	@Autowired
	ProductRepository prepo;

    public Vendor addVendor(Vendor vendor) {
      
    	System.out.println("im in publisher vendor service");
        Vendor v=vrepo.save(vendor);
        return v;
    }
    public List<Vendor> allVendors(){
    	return vrepo.findAll();
    }
    
    public Vendor getVendorById(Long id) {
    	Optional<Vendor>v=vrepo.findById(id);
    	return v.get();
    }
   
    public int countVendors() {
		return vrepo.countTotalVendors();
	}
    public int countActiveVendors() {
		return vrepo.countActiveVendors();
    }
	public Vendor updateVendor(Vendor vendor) {
		// TODO Auto-generated method stub
		return vrepo.save(vendor);
	}
	
}
