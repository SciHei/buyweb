package com.scihei.buyweb.other.model.VO.aftersale.read;

import com.scihei.buyweb.other.model.DO.AfterSale;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class AfterSaleDetailInfoVO extends AfterSaleBasicInfoVO {
    private Integer afterSaleId;
    private Integer orderId;
    private String kind;
    private String reason;
    private AfterSale.State state;
    private String addTime;
}
