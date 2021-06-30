package com.banma.bill.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.banma.bill.mp.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 用户自定义交易类别表
 *
 * @author binglang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("transaction_category")
public class TransactionCategory extends BaseEntity {

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
     * 交易类别名称
     */
    private String name;

    /**
     * 1-支出 2-收入
     */
    private Integer transType;

}
