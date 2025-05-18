package com.tcs.training.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.training.Service.CartService;
import com.tcs.training.dto.CartItemRequestDto;
//@RestController
//@RequestMapping("/cart")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addToCart(@RequestBody CartItemRequestDto request) {
//        return ResponseEntity.ok(cartService.addItemToCart(request));
//    }
//}
import com.tcs.training.dto.CartResponseDto;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItemRequestDto request) {
        try {
            CartResponseDto response = cartService.addItemToCart(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody CartItemRequestDto request) {
        try {
            CartResponseDto response = cartService.updateCartItem(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
        }
    }

    @PostMapping("/remove/{itemId}")
    public ResponseEntity<CartResponseDto> removeCartItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeCartItem(itemId));
    }
}