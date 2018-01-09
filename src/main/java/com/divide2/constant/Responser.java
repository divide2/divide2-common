package com.divide2.constant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by bvvy on 2018/1/9.
 * com.divide2.constant
 */
public class Responser {

    private static final int UPDATE_STATUE = 209;
    private static final int DELETE_STATUS = 210;


    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static ResponseEntity<ReturnCoder> created() {
        return ResponseEntity.status(HttpStatus.CREATED).body(ReturnCoder.ADD_SUCCESS);
    }

    public static  ResponseEntity<ReturnCoder> update() {
        return ResponseEntity.status(UPDATE_STATUE).body(ReturnCoder.UPDATE_SUCCESS);
    }

    public static  ResponseEntity<ReturnCoder> delete() {
        return ResponseEntity.status(DELETE_STATUS).body(ReturnCoder.DELETE_SUCCESS);
    }

    public static  ResponseEntity<String> conflict(String code) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(code);
    }

}
