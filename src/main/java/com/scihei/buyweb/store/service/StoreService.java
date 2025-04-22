package com.scihei.buyweb.store.service;

import com.scihei.buyweb.store.service.DTO.StoreDTO;

import java.util.List;

public interface StoreService {
    StoreDTO getStore(Integer storeId);
    void delete(Integer storeId);
    void update(StoreDTO storeDTO);
    void addStore(StoreDTO storeDTO);
    List<StoreDTO> getStoreList(String fuzzy);
}
