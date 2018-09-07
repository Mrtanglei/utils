package com.utils.wechat.menu.domain;

/**
 * @author tanglei
 * @date 18/9/7 下午4:20
 */
public class WeChatResponse {

    // 错误编号
    private int errcode;

    // 错误信息
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
