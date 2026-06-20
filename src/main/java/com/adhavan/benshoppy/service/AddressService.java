package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.dto.address.CreateAddressRequest;
import com.adhavan.benshoppy.dto.address.UpdateAddressRequest;
import com.adhavan.benshoppy.entity.Address;
import com.adhavan.benshoppy.entity.User;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.mapper.AddressMapper;
import com.adhavan.benshoppy.repository.AddressRepository;
import com.adhavan.benshoppy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    public void addAddress(Long id, CreateAddressRequest dto){

       User user = userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException(" User not found"));
       Address address =  addressMapper.CreateAddressToAddress(dto);
       address.setUser(user);
       addressRepository.save(address);

    }

    public void deleteAddress(Long id){
        addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" "+ id + " : Address id not found"));
        addressRepository.deleteById(id);
    }

    public List<AddressResponse> getAddress(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" User not found"));

        List<Address> addresses = addressRepository.findByUser(user);

      // return addresses.stream().map(addressMapper::AddressToAddressResponse).toList();
        List<AddressResponse> responses = new ArrayList<>();
        for (Address address : addresses){
          AddressResponse addressResponse = new AddressResponse();
          addressResponse.setId(address.getId());
          addressResponse.setAddress(address.getAddress());
          addressResponse.setPinCode(address.getPinCode());
          addressResponse.setNumber(address.getNumber());
          responses.add(addressResponse);
        }
        return responses;
    }

    public void updateAddress(Long id, UpdateAddressRequest dto){

    Address address = addressRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(" "+ id + " : Address id not found"));

     if(dto.getAddress()!=null){
         address.setAddress(dto.getAddress());
     }
     if(dto.getNumber()!=null){
         address.setNumber(dto.getNumber());
     }

     if(dto.getPinCode()!=null){
         address.setNumber(dto.getNumber());
     }

     addressRepository.save(address);

     }



}
