package com.banma.bill.dto.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
public class FlowListVO {

    @ApiModelProperty(value = "总收入")
    private BigDecimal pay;

    @ApiModelProperty(value = "总支出")
    private BigDecimal income;

    @ApiModelProperty(value = "交易笔数")
    private Integer record;

    @ApiModelProperty(value = "交易明细（按日）")
    private List<FlowGroupVO> groupList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FlowGroupVO {

        @JsonFormat(timezone = "GMT+8", locale = "zh", pattern = "MM-dd")
        @ApiModelProperty(value = "交易日期")
        private LocalDate transDate;

        @ApiModelProperty(value = "星期几")
        private String week;

        @ApiModelProperty(value = "当天总支出")
        private BigDecimal pay;

        @ApiModelProperty(value = "当天总收入")
        private BigDecimal income;

        @ApiModelProperty(value = "交易详情列表")
        private List<FlowVO> flowList;
    }
}
