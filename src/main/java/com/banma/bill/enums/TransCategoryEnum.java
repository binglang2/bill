package com.banma.bill.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

/**
 * @author binglang
 * @since 2018/3/1
 */
public enum TransCategoryEnum {

    /**
     * 支出类
     */
    DIET(TransTypeEnum.PAY, "饮食"),

    TRAFFIC(TransTypeEnum.PAY, "交通"),

    LIFE(TransTypeEnum.PAY, "日常生活"),

    PHONE(TransTypeEnum.PAY, "通讯"),

    OTHERS_PAY(TransTypeEnum.PAY, "其他支出"),

    /**
     * 收入类
     */
    WAGE(TransTypeEnum.INCOME, "工资"),

    PART_TIME(TransTypeEnum.INCOME, "兼职"),

    BONUS(TransTypeEnum.INCOME, "奖金"),

    RED_PACKET(TransTypeEnum.INCOME, "红包"),

    OTHERS_INCOME(TransTypeEnum.INCOME, "其他收入");

    @Getter
    private final TransTypeEnum transTypeEnum;

    @Getter
    private final String value;

    TransCategoryEnum(TransTypeEnum transTypeEnum, String value) {
        this.transTypeEnum = transTypeEnum;
        this.value = value;
    }

    public static List<TransCategoryEnum> getByTransTypeEnum(TransTypeEnum transTypeEnum) {
        return Stream.of(values()).filter(p -> p.getTransTypeEnum() == transTypeEnum)
            .collect(Collectors.toList());
    }
}
