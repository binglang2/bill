package com.banma.bill.controller;

import com.banma.bill.common.Result;
import com.banma.bill.dto.vo.FlowListVO;
import com.banma.bill.dto.vo.FlowVO;
import com.banma.bill.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binglang
 * @since 2019/8/12
 */
@Api(tags = "账单流水相关")
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlowController {

    @Autowired
    private FlowService flowService;

    @ApiOperation("交易流水列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "lastDate", value = "分页参数，上次列表最后的 transDate"),
        @ApiImplicitParam(name = "categoryIds", value = "类别 id，多个逗号分隔，为空查所有"),
        @ApiImplicitParam(name = "remark", value = "模糊搜索备注")
    })
    @GetMapping("/flows")
    public Result<FlowListVO> listFlow(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestParam(value = "bookId", required = false) Long bookId,
        @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
        @RequestParam(value = "lastDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate lastDate,
        @RequestParam(value = "categoryIds", required = false) String categoryIds,
        @RequestParam(value = "remark", required = false) String remark) {
        return Result.ofSucceed(flowService
            .listFlow(userId, bookId, startDate, endDate, lastDate, categoryIds, remark));
    }

    @ApiOperation("新增／修改交易流水")
    @PostMapping("/flow")
    public Result<FlowVO> saveFlow(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestBody FlowVO flowVO) {

        return flowService.saveFlow(flowVO, userId) ? Result.ofSucceed(flowVO) : Result.ofFail();
    }

    @ApiOperation("获取用户常用备注")
    @GetMapping("/flow/remarks")
    public Result<List<String>> listRemark(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestParam(value = "count", defaultValue = "6") Integer count) {

        return Result.ofSucceed(flowService.listRemark(userId, count));
    }
}
