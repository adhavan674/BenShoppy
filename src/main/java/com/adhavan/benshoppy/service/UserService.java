package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.dto.user.DetailsUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.SummaryUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.*;
import com.adhavan.benshoppy.entity.*;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.mapper.AddressMapper;
import com.adhavan.benshoppy.mapper.UserMapper;
import com.adhavan.benshoppy.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<SummaryUserOrSellerResponse> searchUser(String name) {

        List<User> users = userRepository.findByNameAndRole(name,Role.USER);
        return users.stream().map(userMapper::UserToSummary).toList();

    }


    public List<SummaryUserOrSellerResponse> searchSeller(String name) {
        List<User> users = userRepository.findByNameAndRole(name,Role.SELLER);
        return users.stream().map(userMapper::UserToSummary).toList();
    }


    public DetailsUserOrSellerResponse detailsUserOrSeller(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(" user not found "));
        List<Address> address = addressRepository.findByUser(user);

        List<AddressResponse> addresses = address.stream().
                map(addressMapper::AddressToAddressResponse).toList();

        DetailsUserOrSellerResponse details = userMapper.UserToDetails(user);
        details.setAddressResponse(addresses);

        return details;


    }

    public List<SummaryUserOrSellerResponse> getActiveOrBlockedUser(Status status) {
        List<User> users = userRepository.findByStatusAndRole(status,Role.USER);
        return users.stream().map(userMapper::UserToSummary).toList();
    }

    public List<SummaryUserOrSellerResponse> getActiveOrBlockedSeller(Status status) {
        List<User> users = userRepository.findByStatusAndRole(status,Role.SELLER);
        return users.stream().map(userMapper::UserToSummary).toList();
    }

    public void updateStatusUserOrSeller(Long id, UpdateStatusRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" user not found "));
        user.setStatus(dto.getStatus());
        userRepository.save(user);
    }

    public AdminCountResponse getAdminStatists() {
        AdminCountResponse statists = new AdminCountResponse();
        statists.setTotalProduct(productRepository.count());
        statists.setTotalCategory(categoryRepository.count());
        statists.setTotalUser(userRepository.countByRole(Role.USER));
        statists.setTotalSeller(userRepository.countByRole(Role.SELLER));
        statists.setTotalOrders(orderRepository.count());

        statists.setActiveUser(userRepository.countByStatusAndRole(Status.ACTIVE,Role.USER));
        statists.setActiveSeller(userRepository.countByStatusAndRole(Status.ACTIVE,Role.SELLER));

        statists.setBlockedUser(userRepository.countByStatusAndRole(Status.BLOCKED,Role.USER));
        statists.setBlockedSeller(userRepository.countByStatusAndRole(Status.BLOCKED,Role.SELLER));


        statists.setActiveProduct(productRepository.countByStatus(Status.ACTIVE));
        statists.setInactiveProduct(productRepository.countByStatus(Status.INACTIVE));
        return statists;
    }

    public SellerCountResponse getSellerStatists(Long id) {
        User user = userRepository.findById(id).orElseThrow();
         Long totalProduct = productRepository.countByUser(user);
         Long activeProduct = productRepository.countByUserAndStatus(user,Status.ACTIVE);
         Long inactiveProduct = productRepository.countByUserAndStatus(user,Status.INACTIVE);

         List<Product> products = productRepository.findByUser(user);

     //    Long totalOrders = orderItemRepository.countByProductin(products);

      return new SellerCountResponse(totalProduct,activeProduct,inactiveProduct);


    }
}


