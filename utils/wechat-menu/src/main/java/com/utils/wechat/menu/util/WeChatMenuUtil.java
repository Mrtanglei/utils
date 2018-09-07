package com.utils.wechat.menu.util;

import com.utils.wechat.menu.domain.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author tanglei
 * @date 18/9/7 下午4:24
 *
 * 自定义菜单栏工具包
 */
public class WeChatMenuUtil {

    /**
     * 删除菜单栏
     *
     * @param appId
     *            第三方用户唯一凭证
     * @param appSecret
     *            第三方用户唯一凭证密钥
     * @throws IOException
     * @throws URISyntaxException
     */
    public static boolean deleteMenu(String appId, String appSecret) {
        WeChatResponse weChatResponse;
        try {
            AccessTokenInfo accessTokenInfo = AccessTokenUtil.getAccessTokenInfo(appId, appSecret);
            if (!accessTokenInfo.isExpire()) {
                weChatResponse = HttpUtil.getWeChatResponse(WeChatURIUtil.buildDeleteMenuMethod(accessTokenInfo.getAccessToken()));
                if (weChatResponse.getErrcode() != 0)
                    throw new RuntimeException("删除公众号菜单失败");
                return true;
            }
            throw new RuntimeException("AccessToken 失效,请重试");
        } catch (Exception e) {
            throw new RuntimeException("删除公众号菜单失败");
        }
    }

    /**
     * 创建自定义菜单栏信息
     *
     * @param appId
     * @param appSecret
     * @param menuReq
     * @return
     */
    public static boolean createMenue(String appId, String appSecret, MenuReq menuReq) {
        if (menuReq != null && menuReq.getButton() != null && menuReq.getButton().size() > 0) {
            Assert.notNull(appId, "appId is null");
            Assert.notNull(appSecret, "appSecret is null");
            checkMenuReq(menuReq);
            WeChatResponse weChatResponse;
            try {
                AccessTokenInfo accessTokenInfo = AccessTokenUtil.getAccessTokenInfo(appId, appSecret);
                if (!accessTokenInfo.isExpire()) {
                    weChatResponse = HttpUtil.getWeChatResponse(WeChatURIUtil.buildCreateMenuMethod(accessTokenInfo.getAccessToken(), menuReq));
                    if (weChatResponse.getErrcode() != 0)
                        throw new RuntimeException("创建公众号菜单失败");
                    return true;
                }
                throw new RuntimeException("AccessToken 失效,请重试");
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        throw new NullPointerException("菜单栏信息为空");
    }

    /**
     * 一些简单校验
     *
     * @param menuReq
     */
    private static void checkMenuReq(MenuReq menuReq) {
        List<WeChatButton> buttons = menuReq.getButton();
        if(buttons.size() >3)
            throw new RuntimeException("一级菜单栏数量不能超过3个");
        for (WeChatButton button : buttons) {
            checkMenu(button);
            if (button.getSub_button() != null && button.getSub_button().size() > 0) {
                for (WeChatButton subButton : button.getSub_button()) {
                    checkMenu(subButton);
                }
            }
        }
    }

    private static void checkMenu(WeChatButton button) {
        Assert.notNull(button.getName(), "菜单栏名称为空");
        checkMenuType(button);
    }

    /**
     * 验证菜单的响应动作类型
     *
     * @param button
     */
    private static void checkMenuType(WeChatButton button) {
        Assert.notNull(button.getType(), "菜单的响应动作类型为空");
        try {
            WeChatMenuType.valueOf(button.getType());
        } catch (Exception e) {
            throw new RuntimeException("菜单的响应动作类型不匹配");
        }
    }

    public static List<WeChatButton> listMenue(String appId, String appSecret) throws URISyntaxException, IOException {
        AccessTokenInfo accessTokenInfo = AccessTokenUtil.getAccessTokenInfo(appId, appSecret);
        if (!accessTokenInfo.isExpire()) {
            return HttpUtil.getWeChatMenuResponse(WeChatURIUtil.buildListMenuMethod(accessTokenInfo.getAccessToken()));
        }
        throw new RuntimeException("AccessToken 失效,请重试");
    }
}
