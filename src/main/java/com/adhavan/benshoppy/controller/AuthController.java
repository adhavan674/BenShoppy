package com.adhavan.benshoppy.controller;


import com.adhavan.benshoppy.dto.auth.LoginResponse;
import com.adhavan.benshoppy.dto.auth.SignupRequest;
import com.adhavan.benshoppy.dto.auth.LoginRequest;
import com.adhavan.benshoppy.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Signup And Login APIs")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/user/signup")
    public String addUser(@Valid @RequestBody SignupRequest dto){

        authService.addUser(dto);

        return "user added";

    }

    @PostMapping("/admin/signup")
    public String addAdmin(@Valid @RequestBody SignupRequest dto){

        authService.addAdmin(dto);

        return "admin added";

    }



    @PostMapping("/seller/signup")
    public String AddSeller(@RequestBody SignupRequest dto){

        authService.addSeller(dto);

        return "Seller added";

    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest dto){
       return authService.login(dto);

    }




}
