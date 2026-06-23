package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.user.DetailsUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.SummaryUserOrSellerResponse;
import com.adhavan.benshoppy.dto.user.*;
import com.adhavan.benshoppy.entity.Status;
import com.adhavan.benshoppy.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SecurityRequirement(name = "bearerAuth")
@Tag(name = " Statistic , User and Seller Management APIs " ,description = " mostly Admin can access  this APIs and seller Overview can access by Seller")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Search User")
    @GetMapping("/admin/user/search")
    public List<SummaryUserOrSellerResponse> searchUser(@RequestParam String name){
        return userService.searchUser(name);
    }

    @Operation(summary = "Search seller")
    @GetMapping("/admin/seller/search")
    public List<SummaryUserOrSellerResponse> searchSeller(@RequestParam String name){
        return userService.searchSeller(name);
    }

    @Operation(summary = "Full Details About User or Seller" ,
            description = " user_id represent both user id and seller id")
    @GetMapping("/admin/{user_id}/details")
    public DetailsUserOrSellerResponse detailsUserOrSeller(@PathVariable Long user_id){
        return userService.detailsUserOrSeller(user_id);
    }

    @Operation(summary = "Get User by Status")
    @GetMapping("/admin/user/status")
    public List<SummaryUserOrSellerResponse> getActiveOrBlockedUser(@RequestParam Status status){
        return userService.getActiveOrBlockedUser(status);
    }


    @Operation(summary = "Get Seller by Status")
    @GetMapping("/admin/seller/status")
    public List<SummaryUserOrSellerResponse> getActiveOrBlockedSeller(@RequestParam Status status){
        return userService.getActiveOrBlockedSeller(status);
    }

    @Operation(summary = "Update Status of User and Seller" ,
            description = " user_id represent both user id and seller id")
    @PatchMapping("/admin/{user_id}/status")
    public String updateStatusOfUserOrSeller(@PathVariable Long user_id,@Valid @RequestBody UpdateStatusRequest dto){
        userService.updateStatusUserOrSeller(user_id,dto);
        return "success";
    }


    @Operation(summary = "Admin Overview")
    @GetMapping("/admin/dashboard")
    public AdminCountResponse getAdminStatists(){
        return userService.getAdminStatists();
    }

    @Operation(summary = "Seller Overview")
    @GetMapping("/seller/{seller_id}/dashboard")
    public SellerCountResponse getSellerStatists(@PathVariable Long seller_id){
        return userService.getSellerStatists(seller_id);
    }


















































}
