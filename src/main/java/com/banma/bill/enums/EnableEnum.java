package com.banma.bill.enums;

import java.util.stream.Stream;
import lombok.Getter;

/**
 * @author binglang
 * @since 2018/3/1
 */
public enum EnableEnum {

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    UN_ENABLE(0, "不启用");

    @Getter
    private final int key;

    @Getter
    private final String value;

    EnableEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static EnableEnum getByKey(int key) {
        return Stream.of(values()).filter(p -> p.getKey() == key).findAny().orElse(UN_ENABLE);
    }
}
