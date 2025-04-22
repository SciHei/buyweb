package com.scihei.buyweb.cart.repository.DO;

import lombok.Data;

@Data
public class Cart {
    private Integer cartId;
    private Integer userId;
    private Integer storeId;
    private Integer productId;
    private Integer number;
    private Integer amount;
    private String addTime;
}
