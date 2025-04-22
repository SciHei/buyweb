package com.scihei.buyweb.other.model.DO;

import lombok.Data;

@Data
public class AfterSale {
    public enum State{
        WAITING, PASSED, REJECTED
    }
    private Integer afterSaleId;
    private Integer orderId;
    private String kind;
    private String reason;
    private State state;
    private String addTime;
}
