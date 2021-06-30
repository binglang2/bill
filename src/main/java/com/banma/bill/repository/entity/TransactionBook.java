package com.banma.bill.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.banma.bill.mp.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 用户账本表
 *
 * @author binglang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("transaction_book")
public class TransactionBook extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * user_id
     */
    private Long userId;

    /**
     * 账本名称
     */
    private String name;

    /**
     * 1-默认账本
     */
    private Integer status;

}
