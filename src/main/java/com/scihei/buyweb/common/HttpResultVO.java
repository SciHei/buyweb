package com.scihei.buyweb.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpResultVO<T> {

    public enum Code{
        SUCCESS(0), FAIL(100);
        private final Integer code;

        Code(Integer i) {
            code = i;
        }
    }
    Integer code;
    T data;
    String message;

    public static <T> HttpResultVO<T> success(T data, String message){
        return new HttpResultVO<>(Code.SUCCESS.code, data, message);
    }

    public static <T> HttpResultVO<T> success(T data){
        return new HttpResultVO<>(Code.SUCCESS.code, data, "");
    }

    public static <T> HttpResultVO<T> fail(T data, String message){
        return new HttpResultVO<>(Code.FAIL.code, data, message);
    }
}
