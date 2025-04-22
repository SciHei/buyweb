package com.scihei.buyweb.product.controller.VO.product.request;

import com.scihei.buyweb.product.repository.DO.Product;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductAdminManageFieldVO extends ProductSelfManageFieldVO {

    private Product.State state;
}
