package com.scihei.buyweb.store.controller.VO.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StoreDetailInfoVO extends StoreBasicInfoVO {
    private Integer userId;
}
