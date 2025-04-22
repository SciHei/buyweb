package com.scihei.buyweb.other.model.VO.order.read;

import com.scihei.buyweb.other.model.DO.Order;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class OrderDetailInfoVO extends OrderBasicInfoVO {
    private Integer orderId;
    private Integer userId;
    private Integer storeId;
    private Integer number;
    private Integer amount;
    private Order.State state;
    private String addTime;
}
