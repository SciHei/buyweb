package com.scihei.buyweb.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scihei.buyweb.cart.repository.CartMapper;
import com.scihei.buyweb.cart.repository.DO.Cart;
import com.scihei.buyweb.cart.service.CartService;
import com.scihei.buyweb.cart.service.DTO.CartDTO;
import com.scihei.buyweb.cart.service.constant.CartServiceExceptionConstants;
import com.scihei.buyweb.common.repositoty.Cache;
import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.common.service.exception.ServiceException;
import com.scihei.buyweb.until.SciHei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    CartMapper cartMapper;
    Cache cache;
    @Autowired
    public CartServiceImpl(CartMapper cartMapper, Cache cache) {
        this.cartMapper = cartMapper;
        this.cache = cache;
    }

    @Override
    public List<CartDTO> getCartList(String username) {
        if(username == null)
            throw new ServiceException(CartServiceExceptionConstants.USER_NULL);
        List<Cart> carts = cartMapper.selectList(new QueryWrapper<Cart>().eq("username", username));
        //整个购物车一起进入缓存
        cache.set(CacheKindConstants.CART, username, carts);
        return (List<CartDTO>) (Object) SciHei.getListByCopy(carts, CartDTO.class);
    }

    @Override
    public void delete(String username, Integer cartId) {
        if(username == null)
            throw new ServiceException(CartServiceExceptionConstants.USER_NULL);
        if(cartId == null)
            throw new ServiceException(CartServiceExceptionConstants.CART_ID_NULL);

        if(cartMapper.deleteByMap(Map.of("username", username, "cart_id", cartId)) == 0)
            throw new ServiceException(CartServiceExceptionConstants.CART_NONENTITY);
        //整个购物车旧数据一起删除
        cache.delete(CacheKindConstants.CART, username);

    }

    @Override
    public void addCart(CartDTO cartDTO) {
        //获取要插入的数据
        Cart cart = (Cart) SciHei.getByCopy(cartDTO, Cart.class);
        // ----



        try{
            cartMapper.insert(cart);
            //可能出现约束问题
        }catch (DuplicateKeyException e){
            //购物车id重复
            throw new ServiceException(CartServiceExceptionConstants.CART_EXISTED);
        }catch (Exception e){
            throw new ServiceException(e.toString());
        }
    }
}
