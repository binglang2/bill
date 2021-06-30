package com.banma.bill.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author binglang
 * @since 2017/11/9
 */
@Slf4j
@RestController
public class BaseController {

    /**
     * 初始化服务器配置时调用
     * <p>
     * 1）将token、timestamp、nonce三个参数进行字典序排序
     * 2）将三个参数字符串拼接成一个字符串进行sha1加密
     * 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * token 自定义配置的值
     *
     * @param echoStr   随机字符串
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 签名
     * @return echoStr
     */
    @RequestMapping("/")
    public String signWxServer(@RequestParam(value = "echostr", defaultValue = "") String echoStr,
        @RequestParam(value = "timestamp", defaultValue = "0") Long timestamp,
        @RequestParam(value = "nonce", defaultValue = "0") Long nonce,
        @RequestParam(value = "signature", defaultValue = "") String signature) {

        // TODO: 签名验证
        log.debug("echoStr: {}", echoStr);
        log.debug("timestamp: {}", timestamp);
        log.debug("nonce: {}", nonce);
        log.debug("signature: {}", signature);
        if (StringUtils.isEmpty(echoStr)) {
            return "Hello, World";
        }
        return echoStr;
    }

}
