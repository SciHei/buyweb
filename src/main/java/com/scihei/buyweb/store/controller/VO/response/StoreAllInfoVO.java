package com.scihei.buyweb.store.controller.VO.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class StoreAllInfoVO extends StoreDetailInfoVO {
    private String addTime;
}
