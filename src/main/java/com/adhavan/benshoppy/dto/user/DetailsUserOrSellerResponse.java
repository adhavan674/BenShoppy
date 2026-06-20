package com.adhavan.benshoppy.dto.user;

import com.adhavan.benshoppy.dto.address.AddressResponse;
import com.adhavan.benshoppy.entity.Role;
import com.adhavan.benshoppy.entity.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DetailsUserOrSellerResponse {

    private Long id;
    private String name;
    private String mail;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
    private List<AddressResponse> addressResponse;

}
