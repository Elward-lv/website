package com.website.springboot.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件的工具类
 * @Author: https://www.jianshu.com/p/4e8217901a95
 */

public class MailUtil {
    protected static Logger logger= LoggerFactory.getLogger(MailUtil.class);

    private final static String NAME = "lvyanweiAccount@163.com";

    private final static String PASSWORD = "QJMFNEEDMSGYQAAZ";

    public static int sendEmail(String toEmail){
        //设置发送邮件的参数
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.163.com");
        properties.put("mail.smtp.port","25");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.auth","true");
        // 设置发送邮件的账号和密码
        Session session = Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(NAME,PASSWORD);
            }
        });
        // 创建邮件对象
        Message message = new MimeMessage(session);

        try {
            // 设置发件人
            message.setFrom(new InternetAddress(NAME));
            // 设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            // 设置主题，在这里要根据业务需求，千万别直接复制粘贴就用了。
            message.setSubject("帐号注册验证");
            // 设置邮件正文 ,因为要生成验证码，所以在此插入正文中
            //我这里是采用六位数随机数字。五位后面*10000，四位*1000。。。
            int volid = (int)((Math.random()*9+1)*100000);
            String content = "系统收到此邮箱帐号在website的注册申请,为确认账户的真实性,须完成账户验证。本次验证码为 <b>"+volid+"</b>,有效时间30分钟。如果不是本账户持有人操作,请忽略!";
            message.setContent(content,"text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return volid;
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("error when send message");
            return 0;
        }
    }
}
