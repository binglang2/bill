package com.banma.bill.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.banma.bill.mp.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * 微信用户表
 *
 * @author binglang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("user_wx")
public class UserWx extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 微信唯一id
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 0 未知， 1 男， 2女
     */
    private Integer sex;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像
     */
    private String avatar;

    private String privilege;

    private String sessionKey;

    private String unionId;

}
