package com.banma.bill.controller;

import com.banma.bill.common.Result;
import com.banma.bill.dto.vo.CategoryTransCountVO;
import com.banma.bill.dto.vo.CategoryVO;
import com.banma.bill.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
@Api(tags = "用户类别相关")
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询用户类别列表")
    @ApiImplicitParam(name = "transType", value = "0-默认查询全部，1-支出的类别，2-收入的类别")
    @GetMapping("/categories")
    public Result<List<CategoryVO>> listCategory(
        @ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestParam(value = "transType", defaultValue = "0") Integer transType,
        @RequestParam(value = "bookId", required = false) Long bookId) {
        List<CategoryVO> categoryVOList = categoryService.listCategory(userId, transType, bookId);
        return Result.ofSucceed(categoryVOList);
    }

    @ApiOperation("新增／修改 类别")
    @PostMapping("/category")
    public Result<CategoryVO> saveCategory(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestBody CategoryVO categoryVO) {
        if ("其他".equals(categoryVO.getName())) {
            return Result.ofFail("类别名称不能为【其他】");
        }
        return categoryService.saveCategory(categoryVO, userId) ? Result.ofSucceed(categoryVO)
            : Result.ofFail();
    }

    @ApiOperation("查询该类别下的交易笔数")
    @GetMapping("/category/trans")
    public Result<CategoryTransCountVO> countTransByCategory(
        @ApiParam(hidden = true) @RequestAttribute Long userId, @RequestParam("id") Long id) {
        return Result.ofSucceed(categoryService.countTransByCategory(userId, id));
    }

    @ApiOperation("删除类别")
    @DeleteMapping("/category/{id}")
    public Result<Void> removeCategory(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @PathVariable("id") Long id) {
        return categoryService.removeCategory(userId, id) ? Result.ofSucceed() : Result.ofFail();
    }
}
