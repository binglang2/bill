package com.banma.bill.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author binglang
 * @since 2017/11/27
 */
public enum ErrorCodeEnum {

    /**
     * success
     */
    SUCCESS(0, "success"),

    FAILED(-1, "failed"),

    ILLEGAL_ARGUMENT(-2, "illegal argument"),

    LOGIN_FAILED(401, "login failed");

    @Getter
    private final int code;

    @Setter
    @Getter
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
