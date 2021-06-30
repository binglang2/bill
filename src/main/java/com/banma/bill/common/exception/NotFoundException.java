package com.banma.bill.common.exception;

import com.banma.bill.common.ErrorCodeEnum;

/**
 * @author binglang
 * @since 2019/8/14
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        this("Not exist or deleted");
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public NotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public NotFoundException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
    }
}
