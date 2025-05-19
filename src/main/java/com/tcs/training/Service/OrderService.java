
package com.tcs.training.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.training.Entity.CartItem;
import com.tcs.training.Entity.OrderStatus;
import com.tcs.training.Entity.PurchaseItem;
import com.tcs.training.Entity.ShippingAddress;
import com.tcs.training.Entity.User;
import com.tcs.training.Entity.UserPurchase;
import com.tcs.training.Repository.CartItemRepository;
import com.tcs.training.Repository.CartRepository;
import com.tcs.training.Repository.OrderRepository;
import com.tcs.training.Repository.ProductRepository;
import com.tcs.training.Repository.UserRepository;
import com.tcs.training.dto.OrderRequestDTO;
import com.tcs.training.dto.OrderResponseDTO;
import com.tcs.training.dto.PurchaseItemDTO;
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                      UserRepository userRepository,
                      ProductRepository productRepository,
                      CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequest) {
        // Get user and cart items
        User user = userRepository.findById(orderRequest.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
       List<CartItem> cartItems=cartItemRepository.findByCart_UserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Calculate totals
        BigDecimal subtotal = calculateSubtotal(cartItems);
        BigDecimal shipping = BigDecimal.ZERO; // Free shipping for now
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.05")); // 5% tax
        BigDecimal total = subtotal.add(shipping).add(tax);

        // Create order
        UserPurchase order = new UserPurchase();
        order.setUser(user);
        order.setInvoiceNumber(generateInvoiceNumber());
        order.setSubtotal(subtotal);
        order.setShipping(shipping);
        order.setTax(tax);
        order.setTotal(total);
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setStatus(OrderStatus.PENDING);
        order.setPurchaseDate(LocalDateTime.now());

        // Set shipping address
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(orderRequest.getAddress());
        shippingAddress.setAddress2(orderRequest.getAddress2());
        shippingAddress.setCity(orderRequest.getCity());
        shippingAddress.setState(orderRequest.getState());
        shippingAddress.setPostalCode(orderRequest.getPostal());
        shippingAddress.setCountry(orderRequest.getCountry());
        shippingAddress.setPhone(orderRequest.getPhone());
        order.setShippingAddress(shippingAddress);

        // Add items to order
        for (CartItem cartItem : cartItems) {
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setProduct(cartItem.getProduct());
            purchaseItem.setQuantity(cartItem.getQuantity());
            purchaseItem.setUnitPrice(BigDecimal.valueOf(cartItem.getProduct().getPrice()));
            purchaseItem.setUserPurchase(order);
            order.getItems().add(purchaseItem);
        }

        // Save order
        order = orderRepository.save(order);

        // Clear cart
        //cartRepository.deleteAllByUserId(user.getId());
       // cartRepository.deleteAll(cartItems);

        return convertToDTO(order);
    }

    public OrderResponseDTO getOrderByInvoice(String invoiceNumber) {
        UserPurchase order = orderRepository.findByInvoiceNumber(invoiceNumber)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        return convertToDTO(order);
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    private BigDecimal calculateSubtotal(List<CartItem> cartItems) {
        return cartItems.stream()
            .map(item -> BigDecimal.valueOf(item.getProduct().getPrice())  // Convert Double to BigDecimal
                .multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderResponseDTO convertToDTO(UserPurchase order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setInvoiceNumber(order.getInvoiceNumber());
        dto.setPurchaseDate(order.getPurchaseDate());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        
        List<PurchaseItemDTO> itemDTOs = order.getItems().stream()
            .map(this::convertItemToDTO)
            .collect(Collectors.toList());
        dto.setItems(itemDTOs);
        
        return dto;
    }

    private PurchaseItemDTO convertItemToDTO(PurchaseItem item) {
        PurchaseItemDTO dto = new PurchaseItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }
}
//@Service
//@Transactional
//public class OrderService {
//
//    private final UserPurchaseRepository purchaseRepository;
//    private final PurchaseItemRepository purchaseItemRepository;
//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
//    private final InvoiceNumberGenerator invoiceNumberGenerator;
//
//    @Autowired
//    public OrderService(UserPurchaseRepository purchaseRepository,
//                      PurchaseItemRepository purchaseItemRepository,
//                      UserRepository userRepository,
//                      ProductRepository productRepository,
//                      InvoiceNumberGenerator invoiceNumberGenerator) {
//        this.purchaseRepository = purchaseRepository;
//        this.purchaseItemRepository = purchaseItemRepository;
//        this.userRepository = userRepository;
//        this.productRepository = productRepository;
//        this.invoiceNumberGenerator = invoiceNumberGenerator;
//    }
//
//    public PurchaseResponseDTO createOrder(PurchaseRequestDTO purchaseRequest) {
//        // Validate user exists
//        User user = userRepository.findById(purchaseRequest.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Create purchase entity
//        UserPurchase purchase = new UserPurchase();
//        purchase.setUser(user);
//        purchase.setStatus(OrderStatus.PENDING);
//        purchase.setPaymentMethod(purchaseRequest.getPaymentMethod());
//        purchase.setInvoiceNumber(invoiceNumberGenerator.generate());
//        
//        // Set shipping address
//        ShippingAddress shippingAddress = new ShippingAddress();
//        ShippingAddressDTO addressDTO = purchaseRequest.getShippingAddress();
//        shippingAddress.setStreet(addressDTO.getStreet());
//        shippingAddress.setApartment(addressDTO.getApartment());
//        shippingAddress.setCity(addressDTO.getCity());
//        shippingAddress.setState(addressDTO.getState());
//        shippingAddress.setZipCode(addressDTO.getZipCode());
//        shippingAddress.setCountry(addressDTO.getCountry());
//        purchase.setShippingAddress(shippingAddress);
//
//        // Process items and calculate totals
//        processItemsAndCalculateTotals(purchaseRequest, purchase);
//
//        // Save purchase
//        UserPurchase savedPurchase = purchaseRepository.save(purchase);
//
//        // Convert to response DTO
//        return convertToResponseDTO(savedPurchase);
//    }
//
//    public PurchaseResponseDTO getOrder(Long id, Long userId) {
//        UserPurchase purchase = purchaseRepository.findByIdAndUserId(id, userId);
//               
//        return convertToResponseDTO(purchase);
//    }
//
//    private void processItemsAndCalculateTotals(PurchaseRequestDTO purchaseRequest, UserPurchase purchase) {
//        BigDecimal subtotal = BigDecimal.ZERO;
//        
//        for (PurchaseItemDTO itemDTO : purchaseRequest.getItems()) {
//            Product product = productRepository.findById(itemDTO.getProductId())
//                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemDTO.getProductId()));
//
//            // Check available quantity
//            if (product.getQuantityAvailable() < itemDTO.getQuantity()) {
//                throw new RuntimeException("Insufficient quantity for product: " + product.getName());
//            }
//
//            // Update product inventory
//            product.setQuantityAvailable(product.getQuantityAvailable() - itemDTO.getQuantity());
//            productRepository.save(product);
//
//            // Create purchase item
//            PurchaseItem item = new PurchaseItem();
//            item.setUserPurchase(purchase);
//            item.setProduct(product);
//            item.setQuantity(itemDTO.getQuantity());
//            item.setUnitPrice(itemDTO.getPrice());
//            
//            purchase.getItems().add(item);
//            subtotal = subtotal.add(itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
//        }
//
//        // Calculate totals
//        purchase.setSubtotal(subtotal);
//        purchase.setShipping(calculateShipping(subtotal));
//        purchase.setTax(calculateTax(subtotal));
//        purchase.setTotal(purchase.getSubtotal()
//                .add(purchase.getShipping())
//                .add(purchase.getTax()));
//    }
//
//    private BigDecimal calculateShipping(BigDecimal subtotal) {
//        // Free shipping for orders over ₹1000, otherwise ₹50
//        return subtotal.compareTo(new BigDecimal("1000.00")) > 0 
//                ? BigDecimal.ZERO 
//                : new BigDecimal("50.00");
//    }
//
//    private BigDecimal calculateTax(BigDecimal subtotal) {
//        // 10% tax
//        return subtotal.multiply(new BigDecimal("0.10"));
//    }
//
//    private PurchaseResponseDTO convertToResponseDTO(UserPurchase purchase) {
//        PurchaseResponseDTO response = new PurchaseResponseDTO();
//        response.setPurchaseId(purchase.getId());
//        response.setInvoiceNumber(purchase.getInvoiceNumber());
//        response.setStatus(purchase.getStatus());
//        response.setTotal(purchase.getTotal());
//        response.setPurchaseDate(purchase.getPurchaseDate());
//        return response;
//    }
//}