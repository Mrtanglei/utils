package com.utils.wechat.menu.domain;

/**
 * @author tanglei
 * @date 18/9/7 下午4:19
 */
public class WeChatConstants {

    public static final String WECHAT_HOST = "api.weixin.qq.com";
    public static final String WECHAT_PROTOCAL = "https";
    // 获取AccessToken url
    public static final String GRANT_TYPE_FOR_ACCRSS_TOKEN = "client_credential";
    public static final String WECHAT_ACCESS_TOKEN_PATH = "/cgi-bin/token";
    // 创建自定义菜单栏url
    public static final String WECHAT_MENU_CREATE_PATH = "/cgi-bin/menu/create";
    // 删除自定义菜单栏url
    public static final String WECHAT_MENU_DELETE_PATH = "/cgi-bin/menu/delete";
    // 查询自定义菜单栏url
    public static final String WECHAT_MENU_LIST_PATH = "/cgi-bin/menu/get";
}
