package com.utils.wechat.menu.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.utils.wechat.menu.domain.MenuReq;
import com.utils.wechat.menu.domain.WeChatConstants;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

/**
 * @author tanglei
 * @date 18/9/7 下午4:24
 */
public class WeChatURIUtil {

    /**
     * 微信接口
     *
     * @return
     */
    private static URIBuilder getWeChatURIBuilder() {
        return new URIBuilder().setScheme(WeChatConstants.WECHAT_PROTOCAL).setHost(WeChatConstants.WECHAT_HOST);
    }

    /**
     * 获取AccessToken的URI
     *
     * @param appId
     * @param appsecret
     * @return
     * @throws URISyntaxException
     */
    private static URI getAccessTokenURI(String appId, String appsecret) throws URISyntaxException {
        return getWeChatURIBuilder().setPath(WeChatConstants.WECHAT_ACCESS_TOKEN_PATH).setParameter("grant_type", WeChatConstants.GRANT_TYPE_FOR_ACCRSS_TOKEN)
                .setParameter("appid", appId).setParameter("secret", appsecret).build();
    }

    /**
     * 删除菜单栏URI
     *
     * @param accessToken
     * @return
     * @throws URISyntaxException
     */
    private static URI getMenuDeleteURI(String accessToken) throws URISyntaxException {
        return getWeChatURIBuilder().setPath(WeChatConstants.WECHAT_MENU_DELETE_PATH).setParameter("access_token", accessToken).build();
    }

    /**
     * 创建自定义菜单栏URI
     *
     * @param accessToken
     * @return
     * @throws URISyntaxException
     */
    private static URI getMenuCreateURI(String accessToken) throws URISyntaxException {
        return getWeChatURIBuilder().setPath(WeChatConstants.WECHAT_MENU_CREATE_PATH).setParameter("access_token", accessToken).build();
    }

    /**
     *
     * @param accessToken
     * @return
     * @throws URISyntaxException
     */
    private static URI getMenuListURI(String accessToken) throws URISyntaxException {
        return getWeChatURIBuilder().setPath(WeChatConstants.WECHAT_MENU_LIST_PATH).setParameter("access_token", accessToken).build();
    }

    /**
     * 获取AccessToken的HttpUriRequest
     *
     * @param appId
     * @param appsecret
     * @return
     * @throws URISyntaxException
     */
    public static HttpUriRequest buildGetAccessTokenMethod(String appId, String appsecret) throws URISyntaxException {
        URI uri = getAccessTokenURI(appId, appsecret);
        HttpGet getMethod = new HttpGet(uri);
        return getMethod;
    }

    /**
     * 删除菜单栏HttpUriRequest
     *
     * @param accessToken
     * @return
     * @throws URISyntaxException
     */
    public static HttpUriRequest buildDeleteMenuMethod(String accessToken) throws URISyntaxException {
        URI uri = getMenuDeleteURI(accessToken);
        HttpGet getMethod = new HttpGet(uri);
        return getMethod;
    }

    /**
     * 创建自定义菜单栏HttpUriRequest
     *
     * @param accessToken
     * @param menuReq
     * @return
     * @throws URISyntaxException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public static HttpUriRequest buildCreateMenuMethod(String accessToken, MenuReq menuReq)
            throws URISyntaxException, JsonProcessingException, UnsupportedEncodingException {
        URI uri = getMenuCreateURI(accessToken);
        HttpPost httpPost = new HttpPost(uri);
        JSONObject jsonObject = new JSONObject(menuReq);
        String jsonString = jsonObject.toString();
        httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
        return httpPost;
    }

    /**
     * 自定义菜单查询
     *
     * @param accessToken
     * @return
     * @throws URISyntaxException
     */
    public static HttpUriRequest buildListMenuMethod(String accessToken) throws URISyntaxException {
        URI uri = getMenuListURI(accessToken);
        HttpGet getMethod = new HttpGet(uri);
        return getMethod;
    }
}
