package com.scihei.buyweb.product.controller.VO.product.response;

import lombok.Data;

@Data
public class ProductSummaryInfoVO {
    private Integer productId;
    private Integer storeId;
    private String name;
    private String imageUrl;
    private Integer price;
}
