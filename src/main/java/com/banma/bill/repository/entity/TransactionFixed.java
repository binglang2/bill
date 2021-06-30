package com.banma.bill.repository.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.banma.bill.mp.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 固定交易信息表
 *
 * @author binglang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("transaction_fixed")
public class TransactionFixed extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 账本 id
     */
    private Long bookId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 0-默认，1～28
     */
    private Integer day;

    /**
     * 交易方式 0-未知，1-支付宝，2-微信，3-现金，4-pos刷卡，5-apple pay，6-其他
     */
    private Integer transWay;

    /**
     * 1-支出 2-收入
     */
    private Integer transType;

    /**
     * 交易类别 id
     */
    private Long categoryId;

    /**
     * 0-每月固定，1-每天固定
     */
    private Integer frequency;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用
     */
    private Integer enabled;

}
