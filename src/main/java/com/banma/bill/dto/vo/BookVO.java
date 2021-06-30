package com.banma.bill.dto.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
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
public class BookVO {

    private Long id;

    @ApiModelProperty(value = "账本名称")
    private String name;

    @ApiModelProperty(value = "1-默认账本")
    private Integer status;

    @ApiModelProperty(value = "账本最近一段时间内的总支出")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "账本最近一段时间内的交易笔数")
    private Integer record;
}
