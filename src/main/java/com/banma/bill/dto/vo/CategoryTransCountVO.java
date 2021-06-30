package com.banma.bill.dto.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author binglang
 * @since 2019/8/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTransCountVO {

    @ApiModelProperty("分类下的交易笔数")
    private Integer transCount;

    @ApiModelProperty("分类下的固定消费数量")
    private Integer fixedCount;
}
