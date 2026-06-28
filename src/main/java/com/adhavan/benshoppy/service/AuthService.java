package com.adhavan.benshoppy.service;


import com.adhavan.benshoppy.config.JwtUtil;
import com.adhavan.benshoppy.dto.auth.LoginResponse;
import com.adhavan.benshoppy.dto.auth.SignupRequest;
import com.adhavan.benshoppy.dto.auth.LoginRequest;
import com.adhavan.benshoppy.entity.Cart;
import com.adhavan.benshoppy.entity.Role;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.entity.User;
import com.adhavan.benshoppy.globalexception.customexception.ResourceAlreadyExistsException;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.mapper.AuthMapper;
import com.adhavan.benshoppy.repository.CartRepository;
import com.adhavan.benshoppy.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    public void addUser(SignupRequest dto){

        Optional<User> user = userRepository.findByMail(dto.getMail());

        if(user.isPresent()){
            throw new ResourceAlreadyExistsException( " " + dto.getMail() + " : Mail is already registered , please Login" );
        }

        User newUser = mapper.SignupRequestToUser(dto);
        newUser.setStatus(Status.ACTIVE);
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(Role.USER);

        userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setUser(newUser);
        cartRepository.save(cart);

    }



    public void addSeller(SignupRequest dto){

        Optional<User> user = userRepository.findByMail(dto.getMail());

        if(user.isPresent()){
            throw new ResourceAlreadyExistsException(" " +  dto.getMail() + " : Mail is already registered , please Login" );
        }

        User newUser = mapper.SignupRequestToUser(dto);
        newUser.setStatus(Status.ACTIVE);
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(Role.SELLER);

        userRepository.save(newUser);


    }


    public LoginResponse login(LoginRequest dto) {

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(dto.getMail(),dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(auth);

        if(authentication.isAuthenticated()){

          User user =  userRepository.findByMail(dto.getMail())
                  .orElseThrow(() -> new ResourceNotFoundException( dto.getMail() +  " Entered Mail is Wrong "));
          String token =  jwtUtil.generateToken( dto.getMail() , user.getId() , user.getRole().name());
          String name = user.getName();
          Long id = user.getId();
          String mail = user.getMail();

          return new LoginResponse(token,name,id,mail);

        }
        else {

            throw new ResourceNotFoundException(" Entered mail or password wrong ");

        }
    }


    public void addAdmin(@Valid SignupRequest dto) {

        Optional<User> user = userRepository.findByMail(dto.getMail());

        if(user.isPresent()){
            throw new ResourceAlreadyExistsException(" " +  dto.getMail() + " : Mail is already registered , please Login" );
        }

        User newUser = mapper.SignupRequestToUser(dto);
        newUser.setStatus(Status.ACTIVE);
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(Role.ADMIN);

        userRepository.save(newUser);


    }
}
