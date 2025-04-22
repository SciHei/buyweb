package com.scihei.buyweb.cart.controller;

import com.scihei.buyweb.cart.service.CartService;
import com.scihei.buyweb.cart.service.DTO.CartDTO;
import com.scihei.buyweb.common.HttpResultVO;
import com.scihei.buyweb.cart.controller.VO.request.CartQueryFieldVO;
import com.scihei.buyweb.cart.controller.VO.response.CartDetailInfoVO;
import com.scihei.buyweb.cart.controller.VO.response.CartSummaryInfoVO;
import com.scihei.buyweb.until.SciHei;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "获得购物车项列表", description = "查询符合查询字段的所有购物车项的概要信息")
    @GetMapping("/carts")
    public HttpResultVO<List<CartSummaryInfoVO>> queryCartSummaryInfoList(
            CartQueryFieldVO cartQueryFieldVO){
        List<CartDTO> cartDTOS = cartService.getCartList(cartQueryFieldVO.getData());
        List<CartSummaryInfoVO> cartSummaryInfoVOS = (List<CartSummaryInfoVO>) (Object) SciHei.getListByCopy(cartDTOS, CartSummaryInfoVO.class);
        return HttpResultVO.success(cartSummaryInfoVOS);
    }

    @Operation(summary = "创建购物车项", description = "输入购物车项详细信息以创建购物车项")
    @PostMapping("/carts")
    public HttpResultVO<Boolean> createCart(
            @RequestBody CartDetailInfoVO cartDetailInfoDto){
        CartDTO cartDTO = (CartDTO) SciHei.getByCopy(cartDetailInfoDto, CartDTO.class);
        cartService.addCart(cartDTO);
        return HttpResultVO.success(true);
    }

    @Operation(summary = "删除购物车项", description = "删除购物车项cart-id")
    @DeleteMapping("/carts/{cart-id}")
    public HttpResultVO<Boolean> deleteCart(
            @PathVariable("cart-id") Integer cartId, @RequestBody String username){
        cartService.delete(username, cartId);
        return HttpResultVO.success(true);
    }
}
