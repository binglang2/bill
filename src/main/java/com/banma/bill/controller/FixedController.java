package com.banma.bill.controller;

import com.banma.bill.common.Result;
import com.banma.bill.dto.vo.FixedVO;
import com.banma.bill.service.FixedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@Api(tags = "固定消费相关")
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class FixedController {

    @Autowired
    private FixedService fixedService;

    @ApiOperation("获取固定消费列表")
    @GetMapping("/fixed")
    public Result<List<FixedVO>> listFixed(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestParam(value = "bookId", required = false) Long bookId) {
        List<FixedVO> fixedVOList = fixedService.listFixed(userId, bookId);
        return Result.ofSucceed(fixedVOList);
    }

    @ApiOperation("新增／修改固定消费")
    @PostMapping("/fixed")
    public Result<FixedVO> saveFixed(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestBody FixedVO fixedVO) {
        return fixedService.saveFixed(fixedVO, userId) ? Result.ofSucceed(fixedVO)
            : Result.ofFail();
    }

    @ApiOperation("删除固定消费")
    @DeleteMapping("/fixed/{id}")
    public Result<Void> removeCategory(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @PathVariable("id") Long id) {
        return fixedService.removeFixed(userId, id) ? Result.ofSucceed() : Result.ofFail();
    }
}
