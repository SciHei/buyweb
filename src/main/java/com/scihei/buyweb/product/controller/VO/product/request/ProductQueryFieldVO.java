package com.scihei.buyweb.product.controller.VO.product.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


//商品查询的字段
@Data
public class ProductQueryFieldVO {
    //三个id只有第一个非空会生效，用以查询
    @JsonProperty(value = "product-id")
    Integer productId;

    @JsonProperty(value = "store-id")
    Integer storeId;

    @JsonProperty(value = "kind-id")
    Integer kindId;

    //是否启用模糊搜索
    //启用则无视id，直接对keywords进行模糊查询
    Boolean fuzzy;
    String keywords;
}
