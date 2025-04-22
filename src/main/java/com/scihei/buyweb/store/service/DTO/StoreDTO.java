package com.scihei.buyweb.store.service.DTO;

import com.scihei.buyweb.store.repository.DO.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {

    private String username;
    private Integer storeId;
    private String name;
    private String imageUrl;
    private Store.State state;
    private String addTime;
}
