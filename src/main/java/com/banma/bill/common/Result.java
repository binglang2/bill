package com.banma.bill.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;

/**
 * @author binglang
 * @since 2017/11/18
 */
public final class Result<T> implements Serializable {

    @Getter
    private final int code;

    @Getter
    private final String msg;

    @Getter
    private final T data;

    @JsonFormat(timezone = "GMT+8", locale = "zh", pattern = "yyyy-MM-dd HH:mm:ss")
    @Getter
    private final LocalDateTime time;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = LocalDateTime.now();
    }

    @JsonIgnore
    public boolean isFailed() {
        return this.code != 0;
    }

    public static <T> Result<T> ofSucceed(T data) {
        return new Result<>(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> ofSucceed() {
        return ofSucceed(null);
    }

    public static <T> Result<T> ofFail(ErrorCodeEnum errorCodeEnum) {
        return new Result<>(errorCodeEnum.getCode(), errorCodeEnum.getMsg(), null);
    }

    public static <T> Result<T> ofFail(String msg) {
        return new Result<>(ErrorCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> Result<T> ofFail() {
        return ofFail(ErrorCodeEnum.FAILED);
    }

}
