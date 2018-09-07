package com.utils.wechat.menu.util;

import com.utils.wechat.menu.domain.AccessTokenInfo;
import org.apache.http.client.methods.HttpUriRequest;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanglei
 * @date 18/9/7 下午4:22
 */
public class AccessTokenUtil {

    private static Map<String, AccessTokenInfo> accessTokenMap = new ConcurrentHashMap<>();

    public static void put(String appId, AccessTokenInfo accessTokenInfo) {
        accessTokenMap.put(appId, accessTokenInfo);
    }

    public static AccessTokenInfo get(String appId) {
        return accessTokenMap.get(appId);
    }

    /**
     * 获取AccessToken
     *
     * @param appId
     *            第三方用户唯一凭证
     * @param appSecret
     *            第三方用户唯一凭证密钥
     * @return
     * @throws URISyntaxException
     * @throws IOException
     */
    public static AccessTokenInfo getAccessTokenInfo(String appId, String appSecret) throws URISyntaxException, IOException {
        AccessTokenInfo accessTokenInfo = accessTokenMap.get(appId);
        if (accessTokenInfo == null || accessTokenInfo.isExpire()) {
            HttpUriRequest httpUriRequest = WeChatURIUtil.buildGetAccessTokenMethod(appId, appSecret);
            accessTokenInfo = getAccessTokenFromHttpUriRequest(httpUriRequest);
            accessTokenMap.put(appId, accessTokenInfo);
        }
        return accessTokenInfo;
    }

    private static AccessTokenInfo getAccessTokenFromHttpUriRequest(HttpUriRequest httpMethd) throws IOException {
        JSONObject jsonObject = HttpUtil.getJSonResponse(httpMethd);
        AccessTokenInfo accessTokenInfo = new AccessTokenInfo();
        accessTokenInfo.setAccessToken(jsonObject.getString("access_token"));
        accessTokenInfo.setExpiresIn(jsonObject.getLong("expires_in"));
        return accessTokenInfo;
    }
}
