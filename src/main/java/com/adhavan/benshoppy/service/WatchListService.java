package com.adhavan.benshoppy.service;

import com.adhavan.benshoppy.dto.watchlist.SummaryProductResponseWatchList;
import com.adhavan.benshoppy.entity.Product;
import com.adhavan.benshoppy.entity.User;
import com.adhavan.benshoppy.entity.WatchList;
import com.adhavan.benshoppy.globalexception.customexception.ResourceNotFoundException;
import com.adhavan.benshoppy.repository.ProductRepository;
import com.adhavan.benshoppy.repository.UserRepository;
import com.adhavan.benshoppy.repository.WatchListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;



    public void addWatchList(Long user_id ,Long product_id){

        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException(" "+  user_id + " user not found "));
        Product product = productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException(" "+  product_id + " Product id not found "));

        WatchList watchList = new WatchList();
        watchList.setUser(user);
        watchList.setProduct(product);
        watchListRepository.save(watchList);


    }

    public void removeWatchList(Long id){
     WatchList watchList = watchListRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("watchlist not found"));
       watchListRepository.deleteById(id);

    }

    public List<SummaryProductResponseWatchList> getWatchLists(Long user_id){
        User user = userRepository.findById(user_id)
        .orElseThrow(() -> new ResourceNotFoundException(" "+  user_id + " user not found "));

        List<WatchList> watchLists =  watchListRepository.findByUser(user);

        List<SummaryProductResponseWatchList> products = new ArrayList<>();

        for (WatchList watchList : watchLists){
        SummaryProductResponseWatchList prd = new SummaryProductResponseWatchList();
        prd.setWatchlist_id(watchList.getId());
        prd.setProduct_id(watchList.getProduct().getId());
        prd.setName(watchList.getProduct().getName());
        prd.setPrice(watchList.getProduct().getPrice());
        prd.setThumbnail(watchList.getProduct().getThumbnail());
        products.add(prd);

        }

        return products;

    }

    @Transactional
    public void removeAll(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(" "+  id + " user not found "));
        watchListRepository.deleteByUser(user);
    }
}
