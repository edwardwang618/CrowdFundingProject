package com.atguigu.crowd.funding.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {
    
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";
    
    public static ResultEntity<String> successWithoutData() {
        return new ResultEntity<>(SUCCESS, NO_MESSAGE, NO_DATA);
    }
    
    public static <E> ResultEntity<E> successWithoutData(E data) {
        return new ResultEntity<>(SUCCESS, NO_MESSAGE, data);
    }
    
    public static <E> ResultEntity<E> failed(E data, String message) {
        return new ResultEntity<>(FAILED, message, data);
    }
    
    private String result;
    private String message;
    private T data;
}
