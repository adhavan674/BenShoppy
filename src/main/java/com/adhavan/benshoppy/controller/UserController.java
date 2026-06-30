package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.user.DetailsUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.SummaryUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.*;
import com.adhavan.benshoppy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class UserController {

    @Autowired
    private UserService userService;


   @GetMapping("/admin/users")
   public List<SummaryUserOrSellerResponse> getUsers(){
       return userService.getUsers();
   }
   @GetMapping("/admin/sellers")
   public List<SummaryUserOrSellerResponse> getSellers(){
       return userService.getSellers();
   }
    @GetMapping("/admin/{user_id}/details")
    public DetailsUserOrSellerResponse detailsUserOrSeller(@PathVariable Long user_id){
        return userService.detailsUserOrSeller(user_id);
    }

    @PatchMapping("/admin/{user_id}/status")
    public String updateStatusOfUserOrSeller(@PathVariable Long user_id,@Valid @RequestBody UpdateStatusRequest dto){
        userService.updateStatusUserOrSeller(user_id,dto);
        return "success";
    }

    @GetMapping("/admin/dashboard")
    public AdminCountResponse getAdminStatists(){
        return userService.getAdminStatists();
    }

    @GetMapping("/seller/{seller_id}/dashboard")
    public SellerCountResponse getSellerStatists(@PathVariable Long seller_id){
        return userService.getSellerStatists(seller_id);
    }


















































}
