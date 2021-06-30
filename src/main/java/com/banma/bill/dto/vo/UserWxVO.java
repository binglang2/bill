package com.banma.bill.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author binglang
 * @since 2020/9/26
 */
@Data
public class UserWxVO {

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "0 未知， 1 男， 2女")
    private Integer sex;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "头像")
    private String avatar;

}
