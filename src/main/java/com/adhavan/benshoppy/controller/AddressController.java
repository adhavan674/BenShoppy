package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.dto.address.CreateAddressRequest;
import com.adhavan.benshoppy.dto.address.UpdateAddressRequest;
import com.adhavan.benshoppy.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{id}")
    public String addAddress(@PathVariable Long id, @Valid @RequestBody CreateAddressRequest dto){
        addressService.addAddress(id,dto);
        return "address added success" ;
    }

    @GetMapping("/{id}")
    public List<AddressResponse> getAddresses(@PathVariable Long id)
    {
        return addressService.getAddress(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
        return "deleted success";
    }

    @PatchMapping("/{id}")
    public String updateAddress(@PathVariable Long id, @Valid @RequestBody UpdateAddressRequest dto){
        addressService.updateAddress(id,dto);
        return "updated success";
    }

}
