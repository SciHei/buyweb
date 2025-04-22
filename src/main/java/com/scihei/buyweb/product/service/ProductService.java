package com.scihei.buyweb.product.service;

import com.scihei.buyweb.product.service.DTO.ProductDTO;

import java.util.List;

public interface ProductService {
    void addProduct(ProductDTO productDTO);
    void deleteProduct(Integer productId);
    void updateProduct(ProductDTO productDTO);
    ProductDTO getProduct(Integer productId);
    List<ProductDTO> getProductList(String name);
}
