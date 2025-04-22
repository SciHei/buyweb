package com.scihei.buyweb.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scihei.buyweb.store.repository.DO.Store;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StoreMapper extends BaseMapper<Store> {
}
