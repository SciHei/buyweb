package com.scihei.buyweb.product.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scihei.buyweb.product.repository.DO.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
