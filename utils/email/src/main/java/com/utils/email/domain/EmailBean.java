package com.utils.email.domain;

import java.util.List;

/**
 * @author tanglei
 * @date 18/9/7 下午4:49
 */
public class EmailBean {

    /**
     * 收件人邮箱，以,分割
     */
    private String to;

    /**
     * 标题
     */
    private String subject;

    /**
     * 内容
     */
    private String body;

    /**
     * 附件路径
     */
    private List<String> filepath;

    /**
     * 发送邮件的服务器的地址
     */
    private String host;

    /**
     * 是否需要身份验证
     */
    private boolean validate = true;

    /**
     * 发送邮件的服务器的端口
     */
    private String port;

    /**
     * 设置发送协议
     */
    private String protocol;

    /**
     * 发送方邮箱账户
     */
    private String userName;

    /**
     * 发送方邮箱账户密码
     */
    private String password;

    /**
     * 发送方名称
     */
    private String nick;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getFilepath() {
        return filepath;
    }

    public void setFilepath(List<String> filepath) {
        this.filepath = filepath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean getValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
