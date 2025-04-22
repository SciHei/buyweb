package com.scihei.buyweb.common.repositoty.constant;

import lombok.Getter;

@Getter
public enum CacheKindConstants {
    USER(1), ROLE(2), STORE(3), CART(4), PRODUCT(5);
    private final Integer kind;
    CacheKindConstants(Integer kind){
        this.kind = kind;
    }
}
