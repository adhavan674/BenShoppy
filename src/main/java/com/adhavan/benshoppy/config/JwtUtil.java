package com.adhavan.benshoppy.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "your secret key";

    public SecretKey getKey(){

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    }

    public String generateToken(String mail , Long id , String role){

        return  Jwts.builder()
                .subject(mail)
                .claim("id",id)
                .claim("role",role)
                .issuedAt(new Date())
                .signWith(getKey())
                .compact();
    }

   public Claims getClaims(String token){

        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

   }

   public String extractUserName(String token){
        return getClaims(token).getSubject();
   }
   public String extractRole(String token){
        return getClaims(token).get("role",String.class);
   }

   public boolean tokenValidation(String token){

        try {

            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return true;
        }catch (BadCredentialsException e){
            return false;
        } catch (Exception e) {
            return false;
        }


   }


}
