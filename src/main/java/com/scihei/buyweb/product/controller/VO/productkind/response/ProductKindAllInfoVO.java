package com.scihei.buyweb.product.controller.VO.productkind.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ProductKindAllInfoVO extends ProductKindBasicInfoVO {
    private String add_time;
}
