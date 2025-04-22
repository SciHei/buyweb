package com.scihei.buyweb.product.controller.VO.productkind.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ProductKindAdminManageFieldVO extends ProductKindSelfManageFieldVO {
    private String kind;
}
