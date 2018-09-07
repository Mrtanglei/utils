package com.utils.wechat.menu.domain;

import java.util.List;

/**
 * @author tanglei
 * @date 18/9/7 下午4:18
 */
public class MenuReq {

    private List<WeChatButton> button;

    public List<WeChatButton> getButton() {
        return button;
    }

    public void setButton(List<WeChatButton> button) {
        this.button = button;
    }
}
