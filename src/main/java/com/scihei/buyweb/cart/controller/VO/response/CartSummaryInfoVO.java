package com.scihei.buyweb.cart.controller.VO.response;

import lombok.Data;

@Data
public class CartSummaryInfoVO {
    private Integer cartId;
    private Integer userId;
    private Integer storeId;
    private Integer productId;
    private Integer number;
    private Integer amount;
    private String addTime;
}
