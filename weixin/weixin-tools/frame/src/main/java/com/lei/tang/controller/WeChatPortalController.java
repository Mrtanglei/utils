package com.lei.tang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanglei
 * @date 18/11/16
 * <p>
 * 微信连接入口
 */
@Slf4j
@RestController
@RequestMapping("portal/{appid}")
public class WeChatPortalController {

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String handle(@PathVariable String appid,
                         @RequestParam(name = "signature",required = false) String signature,
                         @RequestParam(name = "timestamp",required = false) String timestamp,
                         @RequestParam(name = "nonce",required = false)String nonce,
                         @RequestParam(name = "echostr",required = false)String echostr){
        log.info("结束服务器认证消息：{},{},{},{}",signature,timestamp,nonce,echostr);
        Assert.isTrue(StringUtils.hasText(signature) && StringUtils.hasText(timestamp) && StringUtils.hasText(nonce) && StringUtils.hasText(echostr), "请求参数非法，请核实！");
        return echostr;
    }
}
