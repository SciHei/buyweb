package com.scihei.buyweb.store.controller.VO.response;

import com.scihei.buyweb.store.repository.DO.Store;
import lombok.Data;

@Data
public class StoreBasicInfoVO {
    private Integer storeId;
    private String name;
    private String imageUrl;
    private Store.State state;
}
