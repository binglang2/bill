package com.banma.bill.enums;

import java.util.stream.Stream;
import lombok.Getter;

/**
 * @author binglang
 * @since 2018/3/1
 */
public enum TransWayEnum {

    /**
     * 其他
     */
    UNKNOWN(0, "其他"),

    ALIPAY(1, "支付宝"),

    WECHAT(2, "微信"),

    CASH(3, "现金"),

    UNION_CARD(4, "pos刷卡"),

    APPLE_PAY(5, "apple pay"),

    OTHERS(6, "其他");

    @Getter
    private final int key;

    @Getter
    private final String value;

    TransWayEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static TransWayEnum getByKey(int key) {
        return Stream.of(values()).filter(p -> p.getKey() == key).findAny().orElse(OTHERS);
    }
}
