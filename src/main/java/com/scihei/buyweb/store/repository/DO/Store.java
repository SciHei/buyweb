package com.scihei.buyweb.store.repository.DO;

import lombok.Data;

@Data
public class Store {
    public enum State{
        WAITING, PASSED, REJECTED
    }

    private String username;
    private Integer storeId;
    private String name;
    private String imageUrl;
    private State state;
    private String addTime;
}
