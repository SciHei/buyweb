package com.scihei.buyweb.product.service.DTO;

import com.scihei.buyweb.product.repository.DO.Product;
import lombok.Data;

@Data
public class ProductDTO {

    private Integer productId;
    private Integer storeId;
    private String name;
    private String imageUrl;
    private String kind;
    private Integer price;
    private Integer number;
    private Product.State state;
    private String addTime;
}
