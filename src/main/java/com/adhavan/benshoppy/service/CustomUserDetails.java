package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.entity.User;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user =  userRepository.findByMail(username).orElseThrow(() -> new ResourceNotFoundException( username + " Not Found"));



       UserDetails userDetails = org.springframework.security.core.userdetails.
               User.builder()
               .username(user.getMail())
               .password(user.getPassword())
               .roles(String.valueOf(user.getRole())).build();

       return userDetails;

    }
}
