package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.user.DetailsUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.SummaryUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.*;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/admin/user/search")
    public List<SummaryUserOrSellerResponse> searchUser(@RequestParam String name){
        return userService.searchUser(name);
    }


    @GetMapping("/admin/seller/search")
    public List<SummaryUserOrSellerResponse> searchSeller(@RequestParam String name){
        return userService.searchSeller(name);
    }


    @GetMapping("/admin/{user_id}/details")
    public DetailsUserOrSellerResponse detailsUserOrSeller(@PathVariable Long user_id){
        return userService.detailsUserOrSeller(user_id);
    }


    @GetMapping("/admin/user/status")
    public List<SummaryUserOrSellerResponse> getActiveOrBlockedUser(@RequestParam Status status){
        return userService.getActiveOrBlockedUser(status);
    }



    @GetMapping("/admin/seller/status")
    public List<SummaryUserOrSellerResponse> getActiveOrBlockedSeller(@RequestParam Status status){
        return userService.getActiveOrBlockedSeller(status);
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
