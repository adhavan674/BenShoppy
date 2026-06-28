package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.watchlist.SummaryProductResponseWatchList;
import com.adhavan.benshoppy.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/watchlist")
@RestController
public class WatchListController {

    @Autowired
    private WatchListService watchListService;


    @PostMapping("/{userid}/{product_id}")
    public String addWatchList(@PathVariable Long userid,@PathVariable Long product_id){

      watchListService.addWatchList(userid,product_id);
      return "added success full";
   }


   @GetMapping("/{user_id}")
    public List<SummaryProductResponseWatchList> getAllWatchList(@PathVariable Long user_id){
        return watchListService.getWatchLists(user_id);

   }

   @DeleteMapping("/{watchlist_id}")
    public String removeWatchList(@PathVariable Long watchlist_id){
       watchListService.removeWatchList(watchlist_id);
       return "removed successful";
   }


   @DeleteMapping("/all/{user_id}")
    public String removeWatchListAll(@PathVariable Long user_id){
       watchListService.removeAll(user_id);
       return "removed All ";

   }

}
