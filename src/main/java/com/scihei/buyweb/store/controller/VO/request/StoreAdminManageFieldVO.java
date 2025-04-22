package com.scihei.buyweb.store.controller.VO.request;

import com.scihei.buyweb.store.repository.DO.Store;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StoreAdminManageFieldVO extends StoreSelfManageFieldVO {
    private Store.State state;
}
