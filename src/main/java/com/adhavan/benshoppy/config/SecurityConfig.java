package com.adhavan.benshoppy.config;


import com.adhavan.benshoppy.globalexception.CustomAccessDeniedHandler;
import com.adhavan.benshoppy.globalexception.CustomAuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    CustomAuthEntryPoint customAuthEntryPoint;

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http){

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**","/public/**","/swagger-ui/**","/v3/**").permitAll()
                                .requestMatchers("/watchlist/**","/cart/**","/order/**","/user/**").hasRole("USER")
                                .requestMatchers("/images/**","/seller/**").hasRole("SELLER")
                                .requestMatchers("/admin/**","/category/**").hasRole("ADMIN")
                                .requestMatchers("/address/**").hasAnyRole("USER","SELLER")
                                .requestMatchers("/product/{product_id}/status").hasAnyRole("SELLER","ADMIN")
                                 .requestMatchers("/productImage/**","/categoryImage/**","/thumbnail/**").permitAll()
                                        .anyRequest().authenticated())
                .exceptionHandling(ex
                        -> ex.authenticationEntryPoint(customAuthEntryPoint).accessDeniedHandler(customAccessDeniedHandler))
                 .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

   }


   @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
   }

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){

        return config.getAuthenticationManager();
   }

}
