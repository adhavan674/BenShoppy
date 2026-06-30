package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.order.SummaryOrderResponse;
import com.adhavan.benshoppy.dto.order.UpdateOrderStatus;
import com.adhavan.benshoppy.entity.*;
import com.adhavan.benshoppy.globalexception.customexception.InsufficientStockException;
import com.adhavan.benshoppy.globalexception.customexception.OrderAlreadyCancelledException;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.mapper.AddressMapper;
import com.adhavan.benshoppy.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.adhavan.benshoppy.entity.Order;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CartItemRepository cartItemRepository;



    @Transactional
    public void createOrder(Long userId, Long addressId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(" User not found"));
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException(" " +  addressId + " address not found"));

        log.info("fetching cart using user");
        Cart cart = cartRepository.findByUser(user);
        log.info("fetching cartitem using cart");
        List<CartItem> cartItemList = cartItemRepository.findByCart(cart);

        log.info("order object created");
        Order order = new Order();
        order.setAddress(address);
        order.setUser(user);
        order.setPayment(Payment.COD);

        double totalPrice = 0.00 ;

        for (CartItem cartItem : cartItemList){
            totalPrice = totalPrice + cartItem.getQuantity()*cartItem.getProduct().getPrice();
        }
        order.setTotalPrice(totalPrice);
        log.info("order object setup completed");
        log.info("order saving");
        Order newOrder =  orderRepository.save(order);
        log.info("order saved");

        log.info("loop started for cart item to order item");
        for (CartItem cartItem : cartItemList){
         log.info(" orderiteam object creating");
         OrderItem orderItem = new OrderItem();
         log.info("setting order object in order item tables");
         orderItem.setOrder(newOrder);
         log.info("creating product object");
         Product product = productRepository.findById(cartItem.getProduct().getId()).orElseThrow();
         log.info("updating stock in product ");
         product.setStock(product.getStock()-cartItem.getQuantity());
         log.info("checking out of stock");
         if((product.getStock()-cartItem.getQuantity()<0)){  // 10-2 = 8 , 2-3 = -1   -1<0
             throw new InsufficientStockException( product.getName() + " product is out of stock ");
            }
         productRepository.save(product);
          log.info("setting product in order item ");
         orderItem.setProduct(cartItem.getProduct());
         log.info("setting quantity");
         orderItem.setQuantity(cartItem.getQuantity());
         log.info("setting price");
         orderItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
         log.info("setting stataus");
         orderItem.setStatus(OrderStatus.PLACED);
         log.info("saving order item");
         orderItemRepository.save(orderItem);

        }

        cartItemRepository.deleteByCart(cart);
        log.info("order created , request competed");
    }

    @Transactional
    public void cancelOrder(Long id) {

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("order item not found"));
        if(orderItem.getStatus()==OrderStatus.CANCELLED){
            throw new OrderAlreadyCancelledException("order already canceller");
        }
        orderItem.setStatus(OrderStatus.CANCELLED);
        Product product = orderItem.getProduct();
        product.setStock(product.getStock() + orderItem.getQuantity());
        productRepository.save(product);
        orderItemRepository.save(orderItem);
    }

    public List<SummaryOrderResponse> getOrders(Long id) {
     User user = userRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException(" " + id + " User id not found"));
     List<Order> orders = orderRepository.findByUser(user);

        List<SummaryOrderResponse> summary = new ArrayList<>();

     for (Order order : orders) {
         List<OrderItem> orderItems = orderItemRepository.findByOrder(order);


         for (OrderItem orderItem : orderItems) {
             SummaryOrderResponse ord = new SummaryOrderResponse();
             ord.setProduct_id(orderItem.getProduct().getId());
             ord.setOrderItem_id(orderItem.getId());
             ord.setProductName(orderItem.getProduct().getName());
             ord.setStatus(orderItem.getStatus());
             ord.setPrice(orderItem.getPrice() * orderItem.getQuantity());
             ord.setPayment(orderItem.getOrder().getPayment());
             ord.setQuantity(orderItem.getQuantity());
             ord.setPlacedAt(orderItem.getOrder().getPlacedAt());
             ord.setImg(orderItem.getProduct().getThumbnail());
             ord.setAddress(addressMapper.AddressToAddressResponse(orderItem.getOrder().getAddress()));
             summary.add(ord);
         }

     }

     return summary;


    }

    public List<SummaryOrderResponse> getSellerOrders(Long id) {

       User user = userRepository.findById(id)
               .orElseThrow( () -> new ResourceNotFoundException(id + " seller id not found"));
       List<Product> products =  productRepository.findByUser(user);
       List<OrderItem> orderItems = orderItemRepository.findByProductIn(products);

       List<SummaryOrderResponse> summaryOrder = new ArrayList<>();

       for (OrderItem orderItem : orderItems){
        SummaryOrderResponse oneOrder = new SummaryOrderResponse();
        oneOrder.setProduct_id(orderItem.getProduct().getId());
        oneOrder.setOrderItem_id(orderItem.getId());
        oneOrder.setImg(orderItem.getProduct().getThumbnail());
        oneOrder.setProductName(orderItem.getProduct().getName());
        oneOrder.setPrice(orderItem.getPrice()*orderItem.getQuantity());
        oneOrder.setQuantity(orderItem.getQuantity());
        oneOrder.setStatus(orderItem.getStatus());
        oneOrder.setPlacedAt(orderItem.getOrder().getPlacedAt());
        oneOrder.setPayment(orderItem.getOrder().getPayment());
        Order order  = orderItem.getOrder();
        Address address = order.getAddress();
        oneOrder.setAddress(addressMapper.AddressToAddressResponse(address));

        summaryOrder.add(oneOrder);
       }

       return summaryOrder;
    }


    public void updateOrderStatus(Long id, UpdateOrderStatus dto) {

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" " + id + " order item not found "));
        
        orderItem.setStatus(dto.getOrderStatus());
        orderItemRepository.save(orderItem);


    }
}
