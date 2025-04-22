package com.scihei.buyweb.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.scihei.buyweb.common.repositoty.Cache;
import com.scihei.buyweb.common.repositoty.constant.CacheKindConstants;
import com.scihei.buyweb.common.repositoty.constant.RepositoryExceptionConstants;
import com.scihei.buyweb.common.repositoty.exception.RepositoryException;
import com.scihei.buyweb.common.service.exception.ServiceException;
import com.scihei.buyweb.store.repository.DO.Store;
import com.scihei.buyweb.store.repository.StoreMapper;
import com.scihei.buyweb.store.service.DTO.StoreDTO;
import com.scihei.buyweb.store.service.StoreService;
import com.scihei.buyweb.store.service.constant.StoreServiceExceptionConstants;
import com.scihei.buyweb.until.SciHei;
import com.scihei.buyweb.user.service.constant.UserServiceExceptionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreServiceImpl implements StoreService {
    StoreMapper storeMapper;
    Cache cache;
    @Autowired
    public StoreServiceImpl(StoreMapper storeMapper, Cache cache){
        this.storeMapper = storeMapper;
        this.cache = cache;
    }
    @Override
    public StoreDTO getStore(Integer storeId) {
        if(storeId == null)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_ID_NULL);

        Store store = null;
        //从缓存查
        try{
            store = (Store) cache.get(CacheKindConstants.STORE, storeId.toString());
        }catch (RepositoryException e){
            //缓存表示之前查过了并且数据库里也没有，就抛出异常过滤掉该请求
            if(e.getResult().equals(RepositoryExceptionConstants.VALUE_NULL))
                throw new ServiceException(StoreServiceExceptionConstants.STORE_NONENTITY);
        }
        //从数据库查
        if(store == null)
        {
            storeMapper.selectOne(new QueryWrapper<Store>().eq("store_id", storeId));
            //不管是否为空都加入缓存
            cache.set(CacheKindConstants.STORE, storeId.toString(), store);
        }
        //缓存和数据库都没有
        if(store == null)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_NONENTITY);
        return (StoreDTO) SciHei.getByCopy(store, StoreDTO.class);
    }

    @Override
    public void delete(Integer storeId) {
        if(storeId == null)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_ID_NULL);
        //---
        //删的时候要把以它为外键的都删了,以及其他有约束的实体



        //店铺不存在
        if(storeMapper.deleteByMap(Map.of("store_id", storeId)) == 0)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_NONENTITY);
        //删除缓存中的旧数据
        cache.delete(CacheKindConstants.STORE, storeId.toString());
    }

    @Override
    public void update(StoreDTO storeDTO) {
        if(storeDTO.getStoreId() == null)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_ID_NULL);
        //获得更新后的商店信息
        Store store = (Store) SciHei.getByCopy(storeDTO, Store.class);

        if(storeMapper.update(store, new UpdateWrapper<Store>().eq("store_id", storeDTO.getStoreId())) == 0)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_NONENTITY);

        //删除缓存中的旧数据
        cache.delete(CacheKindConstants.STORE, storeDTO.getStoreId().toString());
    }

    @Override
    public void addStore(StoreDTO storeDTO) {
        if(storeDTO.getStoreId() == null)
            throw new ServiceException(StoreServiceExceptionConstants.STORE_ID_NULL);
        //获得要添加的商店信息
        Store store = (Store) SciHei.getByCopy(storeDTO, Store.class);
        //----




        try{
            storeMapper.insert(store);
            //可能出现约束问题
        }catch (DuplicateKeyException e){
            //店铺id重复
            throw new ServiceException(StoreServiceExceptionConstants.STORE_EXISTED);
        }catch (Exception e){
            throw new ServiceException(e.toString());
        }
    }

    @Override
    public List<StoreDTO> getStoreList(String fuzzy) {
        List<Store> stores = storeMapper.selectList(new QueryWrapper<Store>().like("name", SciHei.getFuzzy(fuzzy)));
        return (List<StoreDTO>) (Object) SciHei.getListByCopy(stores, StoreDTO.class);
    }
}
