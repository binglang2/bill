package com.banma.bill.dto.vo;

import com.banma.bill.repository.entity.TransactionFixed;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author binglang
 * @since 2019/8/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedVO {

    private Long id;

    private Long bookId;

    @ApiModelProperty(value = "交易类别名称")
    private String categoryName;

    @ApiModelProperty(value = "交易类别 id")
    private Long categoryId;

    @ApiModelProperty(value = "1-支出 2-收入")
    private Integer transType;

    @ApiModelProperty(value = "交易方式 0-未知，1-支付宝，2-微信，3-现金，4-pos刷卡，5-apple pay，6-其他")
    private Integer transWay;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "0-默认，每月固定时该值为 1～28")
    private Integer day;

    @ApiModelProperty(value = "0-每月固定，1-每天固定")
    private Integer frequency;

    @ApiModelProperty(value = "备注")
    private String remark;

    private LocalDateTime createTime;

    public FixedVO(TransactionFixed fixed) {
        BeanUtils.copyProperties(fixed, this);
        this.categoryName = "";
    }
}
