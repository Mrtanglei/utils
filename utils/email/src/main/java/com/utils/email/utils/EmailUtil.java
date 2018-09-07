package com.utils.email.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.utils.email.domain.EmailBean;

/**
 * @author tanglei
 * @date 18/9/7 下午4:50
 */
public class EmailUtil {

    /**
     * 发送邮件
     * @param bean
     * @return
     * @throws AddressException
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public static boolean sendEmail(EmailBean bean) throws AddressException, UnsupportedEncodingException, MessagingException {
        checkEmailBean(bean);
        decorateParames(bean.getBody(), "");
        decorateParames(bean.getSubject(), "无主题");
        Properties properties = getProperties(bean);
        // 得到默认的对话对象
        Session session = Session.getDefaultInstance(properties, null);
        // 创建一个消息，并初始化该消息的各项元素
        MimeMessage msg = new MimeMessage(session);
        // 设置发送方
        msg.setFrom(new InternetAddress(MimeUtility.encodeText(bean.getNick() == null ? "" : bean.getNick()) + "<" + bean.getUserName() + ">"));
        // 创建收件人列表
        if (bean.getTo() != null && bean.getTo().trim().length() > 0) {
            String[] arr = bean.getTo().split(",");
            int receiverCount = arr.length;
            if (receiverCount > 0) {
                setReceiver(msg, receiverCount, arr, bean.getSubject());
                setContact(bean.getFilepath(), bean.getBody(), msg);
                // 设置信件头的发送日期
                msg.setSentDate(new Date());
                msg.saveChanges();
                // 发送信件
                Transport transport = session.getTransport(bean.getProtocol());
                transport.connect(bean.getUserName(), bean.getPassword());
                transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
                transport.close();
                return true;
            }
        }
        throw new NullPointerException("None receiver!");
    }

    /**
     * 设置邮件内容
     *
     * @param filepath
     * @param body
     * @param msg
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static void setContact(List<String> filepath, String body, MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        // 后面的BodyPart将加入到此处创建的Multipart中
        Multipart mp = new MimeMultipart();
        // 附件操作
        if (filepath != null && filepath.size() > 0) {
            for (String filename : filepath) {
                MimeBodyPart mbp = new MimeBodyPart();
                // 得到数据源
                FileDataSource fds = new FileDataSource(filename);
                // 得到附件本身并至入BodyPart
                mbp.setDataHandler(new DataHandler(fds));
                // 得到文件名同样至入BodyPart
                mbp.setFileName(MimeUtility.encodeText(fds.getName()));
                mp.addBodyPart(mbp);
            }
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText(body);
            mp.addBodyPart(mbp);
            // 移走集合中的所有元素
            filepath.clear();
            // Multipart加入到信件
            msg.setContent(mp);
        } else {
            // 设置邮件正文
            msg.setText(body);
        }
    }

    /**
     * 设置收件信息
     *
     * @param msg
     * @param receiverCount
     * @param arr
     * @param subject
     * @throws MessagingException
     */
    private static void setReceiver(MimeMessage msg, int receiverCount, String[] arr, String subject) throws MessagingException {
        InternetAddress[] address = new InternetAddress[receiverCount];
        for (int i = 0; i < receiverCount; i++) {
            address[i] = new InternetAddress(arr[i]);
        }
        msg.addRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
    }

    /**
     * 获得邮件会话属性
     *
     * @param bean
     * @return
     */
    private static Properties getProperties(EmailBean bean) {
        Properties props = new Properties();
        props.put("mail.smtp.host", bean.getHost());
        props.put("mail.smtp.auth", bean.getValidate() ? "true" : "false");
        props.put("mail.smtp.port", bean.getPort());
        props.put("mail.transport.protocol", bean.getProtocol());
        props.put("mail.smtp.ssl.enable", "true");
        return props;
    }

    /**
     * 字符串修饰
     *
     * @param oldParame
     * @param newParame
     */
    private static void decorateParames(String oldParame, String newParame) {
        if (oldParame == null || oldParame == "")
            oldParame = newParame;
    }

    private static void checkEmailBean(EmailBean bean) {
        if (bean == null)
            throw new NullPointerException("邮件信息为空");
        checkString(bean.getHost(), "HOST is null");
        checkString(bean.getUserName(), "UserName is null");
        checkString(bean.getPort(), "port is null");
        checkString(bean.getProtocol(), "protocol is null");
        checkString(bean.getPassword(), "PASSWORD is null");
    }

    private static void checkString(String str, String msg) {
        if (str == null || str == "")
            throw new NullPointerException(msg);
    }
}
