package com.adhavan.benshoppy.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String name;
    private Long id;
    private String mail;

}
