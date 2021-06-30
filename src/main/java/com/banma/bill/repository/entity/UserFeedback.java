package com.banma.bill.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.banma.bill.mp.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 用户反馈意见表
 *
 * @author binglang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user_feedback")
public class UserFeedback extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * user_id
     */
    private Long userId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 联系方式
     */
    private String contact;

}
