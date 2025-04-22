package com.scihei.buyweb.product.controller.VO.product.request;

import lombok.Data;

@Data
public class ProductSelfManageFieldVO {
    private String name;
    private String imageUrl;
    private String kind;
    private Integer price;
    private Integer number;
}
