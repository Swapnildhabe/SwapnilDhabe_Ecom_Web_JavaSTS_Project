package com.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.model.Cart;
import com.ecom.model.Product;
import com.ecom.model.UserDtls;
import com.ecom.repository.CartRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.CartService;

@Service
public class CartServiceImpl implements CartService {
		
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Cart saveCart(Integer productId, Integer userId) {
			
		UserDtls userDtls = UserRepository.findById(userId).get();
		Product product = productRepository.findById(productId).get();
		
		return null;
	}

	@Override
	public List<Cart> getCartsByUser(Integer userId) {
	
		return null;
	}
	
	
}
