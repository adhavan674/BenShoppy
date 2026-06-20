package com.adhavan.benshoppy.dto.user;

import lombok.Data;

@Data
public class DetailsUserResponse {
    private Long id;
    private String name;
    private String mail;
    private String role;
    private String active;

}
