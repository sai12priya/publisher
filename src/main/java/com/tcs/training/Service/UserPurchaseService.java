package com.tcs.training.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tcs.training.Repository.UserPurchaseRepository;

public class UserPurchaseService {
	 @Autowired
	    private UserPurchaseRepository userpurRepository;
	    
	    @Autowired
	    private CartService cartService;
	    
	    @Autowired
	    private ProductService productService;
}
