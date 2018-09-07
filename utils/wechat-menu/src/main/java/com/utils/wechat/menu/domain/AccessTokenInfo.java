package com.utils.wechat.menu.domain;

import java.util.Date;

/**
 * @author tanglei
 * @date 18/9/7 下午4:17
 */
public class AccessTokenInfo {

    // 获取到的微信公众号凭证
    private String accessToken;

    // 凭证有效时间，单位：秒
    private long expiresIn;

    // 创建时间
    private long createTime;

    public AccessTokenInfo() {
        this.createTime = (new Date()).getTime();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    // 提前30秒重新获取
    public boolean isExpire() {
        return (new Date()).getTime() - createTime > (expiresIn - 30) * 1000;
    }
}
