package com.vps.demo.po;

import lombok.Data;

/**
 * @author zhulongkun20@163.com
 * @since 2021/6/13 8:04 PM
 */
@Data
public class Response<T> {

    private int code;

    private String message;

    private T data;
}
