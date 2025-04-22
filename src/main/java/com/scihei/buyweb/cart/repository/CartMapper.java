package com.scihei.buyweb.cart.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scihei.buyweb.cart.repository.DO.Cart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartMapper extends BaseMapper<Cart> {
}
