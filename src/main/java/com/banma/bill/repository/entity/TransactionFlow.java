package com.banma.bill.repository.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.banma.bill.mp.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 交易流水表
 *
 * @author binglang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("transaction_flow")
public class TransactionFlow extends BaseEntity {

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
     * 交易日期
     */
    private LocalDate transDate;

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
     * 备注
     */
    private String remark;

    /**
     * 默认-0，transaction_fixed id
     */
    private Long fixedId;

}
