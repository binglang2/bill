package com.banma.bill.common.exception;

import com.banma.bill.common.ErrorCodeEnum;
import lombok.Getter;

/**
 * @author binglang
 * @since 2019/8/14
 */
public class BusinessException extends RuntimeException {

    @Getter
    private final ErrorCodeEnum errorCodeEnum;

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public BusinessException(String msg) {
        super(msg);
        this.errorCodeEnum = ErrorCodeEnum.FAILED;
        this.errorCodeEnum.setMsg(msg);
    }

    /**
     * Constructs a new runtime exception with {@code null} as its detail message.  The cause is not
     * initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
    }
}
