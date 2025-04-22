package com.scihei.buyweb.cart.controller.VO.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class CartDetailInfoVO extends CartBasicInfoVO {
    private Integer cartId;
    private Integer userId;
    private Integer storeId;
    private Integer productId;
    private Integer number;
    private Integer amount;
    private String addTime;
}
