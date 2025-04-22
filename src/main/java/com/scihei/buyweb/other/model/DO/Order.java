package com.scihei.buyweb.other.model.DO;

import lombok.Data;

@Data
public class Order {
    public enum State{
        NON_PAY, NON_SHIP, SHIPPED, RECEIVE, CANCELED
    }
    private Integer orderId;
    private Integer userId;
    private Integer storeId;
    private Integer number;
    private Integer amount;
    private State state;
    private String addTime;
}
