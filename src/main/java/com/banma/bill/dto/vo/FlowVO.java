package com.banma.bill.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author binglang
 * @since 2019/8/12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowVO {

    private Long id;

    private Long bookId;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @JsonFormat(timezone = "GMT+8", locale = "zh", pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "交易日期")
    private LocalDate transDate;

    @ApiModelProperty(value = "交易方式 0-未知，1-支付宝，2-微信，3-现金，4-pos刷卡，5-apple pay，6-其他")
    private Integer transWay;

    @ApiModelProperty(value = "交易类别名称")
    private String categoryName;

    @ApiModelProperty(value = "交易类别 id")
    private Long categoryId;

    @ApiModelProperty(value = "1-支出 2-收入")
    private Integer transType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "默认-0，transaction_fixed id")
    private Long fixedId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
