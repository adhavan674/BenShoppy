package com.adhavan.benshoppy.dto.watchlist;

import lombok.Data;

@Data
public class SummaryProductResponseWatchList {
    private Long watchlist_id;
    private Long product_id;
    private String name;
    private Double price;
    private String thumbnail;
}
