package com.banma.bill.enums;

import lombok.Getter;

/**
 * @author binglang
 * @since 2018/3/1
 */
public enum FrequencyEnum {

    /**
     * 每月固定
     */
    MONTH(0, "每月固定"),

    DAILY(1, "每天固定");

    @Getter
    private final int key;

    @Getter
    private final String value;

    FrequencyEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

}
