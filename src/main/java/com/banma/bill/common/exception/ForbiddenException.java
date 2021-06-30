package com.banma.bill.common.exception;

import com.banma.bill.common.ErrorCodeEnum;

/**
 * @author binglang
 * @since 2019/8/17
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        this("Forbidden operation");
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public ForbiddenException(String msg) {
        super(msg);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public ForbiddenException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
    }
}
