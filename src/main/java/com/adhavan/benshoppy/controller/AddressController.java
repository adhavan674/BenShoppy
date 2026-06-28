package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.dto.address.CreateAddressRequest;
import com.adhavan.benshoppy.dto.address.UpdateAddressRequest;
import com.adhavan.benshoppy.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequestMapping("/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping("/{user_id}")
    public String addAddress(@PathVariable Long user_id, @Valid @RequestBody CreateAddressRequest dto){
        addressService.addAddress(user_id,dto);
        return "address added success" ;
    }


    @GetMapping("/{user_id}")
    public List<AddressResponse> getAddresses(@PathVariable Long user_id)
    {
        return addressService.getAddress(user_id);
    }

    @DeleteMapping("/{address_id}")
    public String deleteAddress(@PathVariable Long address_id){
        addressService.deleteAddress(address_id);
        return "deleted success";
    }


    @PatchMapping("/{address_id}")
    public String updateAddress(@PathVariable Long address_id, @Valid @RequestBody UpdateAddressRequest dto){
        addressService.updateAddress(address_id,dto);
        return "updated success";
    }

}
