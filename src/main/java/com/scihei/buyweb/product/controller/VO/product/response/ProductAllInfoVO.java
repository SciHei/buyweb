package com.scihei.buyweb.product.controller.VO.product.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ProductAllInfoVO extends ProductDetailInfoVO {
    private String addTime;
}
