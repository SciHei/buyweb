package com.scihei.buyweb.cart.service;

import com.scihei.buyweb.cart.service.DTO.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getCartList(String username);
    void delete(String username, Integer cartId);
    void addCart(CartDTO cartDTO);
}
