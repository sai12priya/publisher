
package com.tcs.training.Service;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.training.Entity.InvoiceNumberGenerator;
import com.tcs.training.Entity.OrderStatus;
import com.tcs.training.Entity.Product;
import com.tcs.training.Entity.PurchaseItem;
import com.tcs.training.Entity.ShippingAddress;
import com.tcs.training.Entity.User;
import com.tcs.training.Entity.UserPurchase;
import com.tcs.training.Repository.ProductRepository;
import com.tcs.training.Repository.PurchaseItemRepository;
import com.tcs.training.Repository.UserPurchaseRepository;
import com.tcs.training.Repository.UserRepository;
import com.tcs.training.dto.PurchaseItemDTO;
import com.tcs.training.dto.PurchaseRequestDTO;
import com.tcs.training.dto.PurchaseResponseDTO;
import com.tcs.training.dto.ShippingAddressDTO;

@Service
@Transactional
public class OrderService {

    private final UserPurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final InvoiceNumberGenerator invoiceNumberGenerator;

    @Autowired
    public OrderService(UserPurchaseRepository purchaseRepository,
                      PurchaseItemRepository purchaseItemRepository,
                      UserRepository userRepository,
                      ProductRepository productRepository,
                      InvoiceNumberGenerator invoiceNumberGenerator) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.invoiceNumberGenerator = invoiceNumberGenerator;
    }

    public PurchaseResponseDTO createOrder(PurchaseRequestDTO purchaseRequest) {
        // Validate user exists
        User user = userRepository.findById(purchaseRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create purchase entity
        UserPurchase purchase = new UserPurchase();
        purchase.setUser(user);
        purchase.setStatus(OrderStatus.PENDING);
        purchase.setPaymentMethod(purchaseRequest.getPaymentMethod());
        purchase.setInvoiceNumber(invoiceNumberGenerator.generate());
        
        // Set shipping address
        ShippingAddress shippingAddress = new ShippingAddress();
        ShippingAddressDTO addressDTO = purchaseRequest.getShippingAddress();
        shippingAddress.setStreet(addressDTO.getStreet());
        shippingAddress.setApartment(addressDTO.getApartment());
        shippingAddress.setCity(addressDTO.getCity());
        shippingAddress.setState(addressDTO.getState());
        shippingAddress.setZipCode(addressDTO.getZipCode());
        shippingAddress.setCountry(addressDTO.getCountry());
        purchase.setShippingAddress(shippingAddress);

        // Process items and calculate totals
        processItemsAndCalculateTotals(purchaseRequest, purchase);

        // Save purchase
        UserPurchase savedPurchase = purchaseRepository.save(purchase);

        // Convert to response DTO
        return convertToResponseDTO(savedPurchase);
    }

    public PurchaseResponseDTO getOrder(Long id, Long userId) {
        UserPurchase purchase = purchaseRepository.findByIdAndUserId(id, userId);
               
        return convertToResponseDTO(purchase);
    }

    private void processItemsAndCalculateTotals(PurchaseRequestDTO purchaseRequest, UserPurchase purchase) {
        BigDecimal subtotal = BigDecimal.ZERO;
        
        for (PurchaseItemDTO itemDTO : purchaseRequest.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.getProductId()));

            // Check available quantity
            if (product.getQuantityAvailable() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient quantity for product: " + product.getName());
            }

            // Update product inventory
            product.setQuantityAvailable(product.getQuantityAvailable() - itemDTO.getQuantity());
            productRepository.save(product);

            // Create purchase item
            PurchaseItem item = new PurchaseItem();
            item.setUserPurchase(purchase);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getPrice());
            
            purchase.getItems().add(item);
            subtotal = subtotal.add(itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
        }

        // Calculate totals
        purchase.setSubtotal(subtotal);
        purchase.setShipping(calculateShipping(subtotal));
        purchase.setTax(calculateTax(subtotal));
        purchase.setTotal(purchase.getSubtotal()
                .add(purchase.getShipping())
                .add(purchase.getTax()));
    }

    private BigDecimal calculateShipping(BigDecimal subtotal) {
        // Free shipping for orders over ₹1000, otherwise ₹50
        return subtotal.compareTo(new BigDecimal("1000.00")) > 0 
                ? BigDecimal.ZERO 
                : new BigDecimal("50.00");
    }

    private BigDecimal calculateTax(BigDecimal subtotal) {
        // 10% tax
        return subtotal.multiply(new BigDecimal("0.10"));
    }

    private PurchaseResponseDTO convertToResponseDTO(UserPurchase purchase) {
        PurchaseResponseDTO response = new PurchaseResponseDTO();
        response.setPurchaseId(purchase.getId());
        response.setInvoiceNumber(purchase.getInvoiceNumber());
        response.setStatus(purchase.getStatus());
        response.setTotal(purchase.getTotal());
        response.setPurchaseDate(purchase.getPurchaseDate());
        return response;
    }
}