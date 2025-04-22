package com.scihei.buyweb.product.repository.DO;

import lombok.Data;

@Data
public class Product {
    public enum State{
        WAITING, AVAILABLE, REJECTED, INSUFFICIENT
    }

    private Integer productId;
    private Integer storeId;
    private String name;
    private String imageUrl;
    private String kind;
    private Integer price;
    private Integer number;
    private State state;
    private String addTime;
}
