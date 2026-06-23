package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.watchlist.SummaryProductResponseWatchList;
import com.adhavan.benshoppy.service.WatchListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "WatchList APIs" ,description = " User can assess this APIs")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/watchlist")
@RestController
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @Operation(summary = "Add product to WatchList " )
    @PostMapping("/{userid}/{product_id}")
    public String addWatchList(@PathVariable Long userid,@PathVariable Long product_id){

      watchListService.addWatchList(userid,product_id);
      return "added success full";
   }

   @Operation(summary = "Get All products in WatchList")
   @GetMapping("/{user_id}")
    public List<SummaryProductResponseWatchList> getAllWatchList(@PathVariable Long user_id){
        return watchListService.getWatchLists(user_id);

   }

   @Operation(summary = "Delete one product From WatchList")
   @DeleteMapping("/{watchlist_id}")
    public String removeWatchList(@PathVariable Long watchlist_id){
       watchListService.removeWatchList(watchlist_id);
       return "removed successful";
   }

   @Operation(summary = "Delete All products From WatchList")
   @DeleteMapping("/all/{user_id}")
    public String removeWatchListAll(@PathVariable Long user_id){
       watchListService.removeAll(user_id);
       return "removed All ";

   }

}
