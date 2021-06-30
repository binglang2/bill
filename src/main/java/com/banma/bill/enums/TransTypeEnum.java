package com.banma.bill.enums;

import java.util.stream.Stream;
import lombok.Getter;

/**
 * @author binglang
 * @since 2018/3/1
 */
public enum TransTypeEnum {

    /**
     * 支出
     */
    PAY(1),

    /**
     * 收入
     */
    INCOME(2),

    /**
     * 其他
     */
    OTHERS(0);

    @Getter
    private final int key;

    TransTypeEnum(int key) {
        this.key = key;
    }

    public static TransTypeEnum getByKey(int key) {
        return Stream.of(values()).filter(p -> p.getKey() == key).findAny().orElse(PAY);
    }
}
