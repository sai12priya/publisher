package com.tcs.training.Service;


import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.training.Entity.Cart;
import com.tcs.training.Entity.CartItem;
import com.tcs.training.Entity.Product;
import com.tcs.training.Entity.User;
import com.tcs.training.Repository.CartItemRepository;
import com.tcs.training.Repository.CartRepository;
import com.tcs.training.Repository.ProductRepository;
import com.tcs.training.Repository.UserRepository;
import com.tcs.training.dto.CartItemRequestDto;
import com.tcs.training.dto.CartResponseDto;
import com.tcs.training.dto.CartResponseDto.CartItemDto;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public CartResponseDto addItemToCart(CartItemRequestDto request) {
        // Basic validation
        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Find user and product
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Find or create cart
        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        // Calculate total quantity (existing + new)
        int existingQuantity = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .mapToInt(CartItem::getQuantity)
                .sum();
        
        int totalRequestedQuantity = existingQuantity + request.getQuantity();

        // Check stock against total requested quantity
        if (product.getQuantityAvailable() < totalRequestedQuantity) {
            throw new IllegalArgumentException(
                "Cannot add " + request.getQuantity() + " items. " +
                "You already have " + existingQuantity + " in cart. " +
                "Only " + (product.getQuantityAvailable() - existingQuantity) + " more available."
            );
        }

        // Update or add item
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(request.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cart.getItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }

        return convertToDto(cart);
    }
    public CartResponseDto getCart(int userId) {
        Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));
        return convertToDto(cart);
    }

    public CartResponseDto updateCartItem(CartItemRequestDto request) {
        CartItem cartItem = cartItemRepository.findById(request.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        
        Product product = cartItem.getProduct();
        int currentCartQuantity = cartItem.getQuantity();
        int newQuantity = request.getQuantity();
        
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        // Check if we're increasing the quantity
        if (newQuantity > currentCartQuantity) {
            int quantityIncrease = newQuantity - currentCartQuantity;
            int availableQuantity = product.getQuantityAvailable();
            
            if (availableQuantity < quantityIncrease) {
                throw new IllegalArgumentException(
                    "Cannot increase quantity to " + newQuantity + ". " +
                    "Only " + availableQuantity + " available in stock. " +
                    "Current quantity in cart: " + currentCartQuantity
                );
            }
        }

        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        
        return getCart(request.getUserId());
    }

    public CartResponseDto removeCartItem(Long itemId) {
        CartItem cartItem = cartItemRepository.findById(itemId)
            .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        
        int userId = cartItem.getCart().getUser().getId();
        
        cartItemRepository.delete(cartItem);
        
        return getCart(userId);
    }
    
    private CartResponseDto convertToDto(Cart cart) {
        CartResponseDto response = new CartResponseDto();
        response.setId(cart.getId());
        response.setUserId(cart.getUser().getId());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());
        
        response.setItems(cart.getItems().stream().map(item -> {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setPrice(item.getProduct().getPrice());
            itemDto.setQuantity(item.getQuantity());
            return itemDto;
        }).collect(Collectors.toList()));
        
        return response;
    }
    
    
}