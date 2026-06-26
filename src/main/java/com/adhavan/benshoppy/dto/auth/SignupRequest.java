package com.adhavan.benshoppy.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = " name must be letter ")
    @Size(min = 3,max = 20)
    private String name;

    @Email
    @NotBlank
    private String mail;

    @NotBlank
    @Size(min = 5,max = 20)
    private String password;

}
