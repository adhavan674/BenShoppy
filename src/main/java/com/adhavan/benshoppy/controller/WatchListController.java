package com.adhavan.benshoppy.controller;

import com.adhavan.benshoppy.dto.watchlist.SummaryProductResponseWatchList;
import com.adhavan.benshoppy.service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchListController {

    @Autowired
    private WatchListService watchListService;

    @PostMapping("/{userid}/{product_id}")
    public String addWatchList(@PathVariable Long userid,@PathVariable Long product_id){

      watchListService.addWatchList(userid,product_id);
      return "added success full";
   }

   @GetMapping("/{id}")
    public List<SummaryProductResponseWatchList> getAllWatchList(@PathVariable Long id){
        return watchListService.getWatchLists(id);

   }

   @DeleteMapping("/{id}")
    public String removeWatchList(@PathVariable Long id){
       watchListService.removeWatchList(id);
       return "removed successful";
   }

   @DeleteMapping("/{id}/all")
    public String removeWatchListAll(@PathVariable Long id){
       watchListService.removeAll(id);
       return "removed All ";

   }

}
