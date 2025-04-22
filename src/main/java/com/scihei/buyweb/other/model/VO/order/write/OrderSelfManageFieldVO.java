package com.scihei.buyweb.other.model.VO.order.write;

import com.scihei.buyweb.other.model.DO.Order;
import lombok.Data;

@Data
public class OrderSelfManageFieldVO {
    private Order.State state;
}
