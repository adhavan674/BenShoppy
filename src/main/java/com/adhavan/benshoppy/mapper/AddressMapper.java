package com.adhavan.benshoppy.mapper;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.dto.address.CreateAddressRequest;
import com.adhavan.benshoppy.dto.address.UpdateAddressRequest;
import com.adhavan.benshoppy.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address CreateAddressToAddress(CreateAddressRequest dto);
    AddressResponse AddressToAddressResponse(Address address);


}
