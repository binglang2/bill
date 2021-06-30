package com.banma.bill.controller;

import com.banma.bill.common.Result;
import com.banma.bill.dto.vo.BookVO;
import com.banma.bill.service.BookService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binglang
 * @since 2019/8/17
 */
@Api(tags = "账本相关")
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation("获取账本列表")
    @GetMapping("/books")
    public Result<List<BookVO>> listBook(@ApiParam(hidden = true) @RequestAttribute Long userId) {
        List<BookVO> bookVOList = bookService.listBook(userId);
        return Result.ofSucceed(bookVOList);
    }

    @ApiOperation("新增／修改账本")
    @PostMapping("/book")
    public Result<BookVO> saveBook(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestBody BookVO bookVO) {
        return bookService.saveBook(userId, bookVO) ? Result.ofSucceed(bookVO) : Result.ofFail();
    }

    @ApiOperation("删除账本(删除时提示用户将会删除该账本下的所有账单及固定支出配置)")
    @DeleteMapping("/book/{id}")
    public Result<Void> remove(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @PathVariable("id") Long id) {
        return bookService.removeBook(userId, id) ? Result.ofSucceed() : Result.ofFail();
    }
}
