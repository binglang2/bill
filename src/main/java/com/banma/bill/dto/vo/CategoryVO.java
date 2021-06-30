package com.banma.bill.dto.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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
public class CategoryVO {

    private Long id;

    private Long bookId;

    @ApiModelProperty(value = "交易类别名称")
    private String name;

    @ApiModelProperty("1-支出 2-收入")
    private Integer transType;
}
