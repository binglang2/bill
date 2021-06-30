package com.banma.bill.controller;

import com.banma.bill.common.Result;
import com.banma.bill.repository.UserFeedbackRepository;
import com.banma.bill.repository.entity.UserFeedback;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binglang
 * @since 2020/10/10
 */
@Api(tags = "用户反馈相关接口")
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @ApiOperation("保存用户反馈")
    @PostMapping("/feedback")
    public Result<Void> saveFeedback(
        @ApiParam(hidden = true) @RequestAttribute Long userId,
        @RequestParam String content, @RequestParam String contact) {
        if (StringUtils.isBlank(content)) {
            return Result.ofFail("content can't be blank.");
        }
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setUserId(userId);
        userFeedback.setContent(content);
        userFeedback.setContact(contact);
        return userFeedbackRepository.insert(userFeedback) > 0 ? Result.ofSucceed()
            : Result.ofFail();
    }
}
