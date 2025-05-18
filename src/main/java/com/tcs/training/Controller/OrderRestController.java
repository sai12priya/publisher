package com.tcs.training.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.training.Service.OrderService;
import com.tcs.training.dto.PurchaseRequestDTO;
import com.tcs.training.dto.PurchaseResponseDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> createOrder(
            @RequestBody PurchaseRequestDTO purchaseRequest) {
        PurchaseResponseDTO response = orderService.createOrder(purchaseRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDTO> getOrder(
            @PathVariable Long id,
            @RequestParam Long userId) {
        PurchaseResponseDTO response = orderService.getOrder(id, userId);
        return ResponseEntity.ok(response);
    }
}