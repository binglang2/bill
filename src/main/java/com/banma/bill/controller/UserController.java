package com.banma.bill.controller;

import com.banma.bill.common.ErrorCodeEnum;
import com.banma.bill.common.Result;
import com.banma.bill.common.exception.BusinessException;
import com.banma.bill.dto.vo.UserWxVO;
import com.banma.bill.service.UserWxService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Api(tags = "微信授权及登录相关")
@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserWxService userWxService;

    @ApiOperation("微信授权登录(根据授权后重定向返回的code值登录，返回值为用于登录校验的 token)")
    @PostMapping("/wx/login")
    public Result<String> login(@RequestParam String code) {
        if (StringUtils.isBlank(code)) {
            throw new BusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        return Result.ofSucceed(userWxService.authCode2Session(code));
    }

    @ApiOperation("保存微信用户信息")
    @PostMapping("/wx/info")
    public Result<Boolean> saveUser(@ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestBody UserWxVO userVO) {
        return Result.ofSucceed(userWxService.saveUserInfo(userId, userVO));
    }
}
