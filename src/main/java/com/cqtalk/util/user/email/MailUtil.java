package com.cqtalk.util.user.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {

    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender mailSender;

    // 发邮件的人固定
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param to 发送的对象, 发给谁
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public boolean sendMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(helper.getMimeMessage());
            return true;
        } catch (MessagingException e) {
            logger.error("发送邮件失败: "+e.getMessage());
            return false;
        }
    }

    /**
     * 发送邮件
     * @param title 标题
     * @param titleWithName 是否在标题后添加名称
     * @param content 邮件内容
     * @param contentWithName 是否在内容后添加名称
     * @param email 接收者的邮箱
     */
    private void sendNormalEmail(String title, boolean titleWithName, String content, boolean contentWithName, String email){
        String dName="创琦杂谈网站";
        MimeMessage mimeMessage=null;
        try{
            mimeMessage = mailSender.createMimeMessage(); //创建要发送的消息
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(new InternetAddress(from, dName,"UTF-8")); //设置发送者是谁
            helper.setTo(email); //接收者邮箱
            title = titleWithName?title+"-"+dName:title; //标题内容
            helper.setSubject(title); //发送邮件的标题
            if(contentWithName) {
                content +="<p style='text-align:right'>"+dName+"</p>";
                content +="<p style='text-align:right'>"+"</p>";
            }
            helper.setText(content, true); //发送的内容 是否为HTML
        }catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    /**
     * 发送注册验证码
     * @param email 接收者的邮箱
     * @param code 验证码
     */
    public void sendRegisterCode(final String email, String code) {
        final StringBuffer sb=new StringBuffer(); //实例化一个StringBuffer
        // font-weight: bold
        sb.append("<h4>"+"亲爱的"+email+"：</h4>").append("<p style='text-align: center; font-size: 16px; '>您好！您正在注册创琦杂谈网站，注册验证码为: "+code
                +"。5分钟内有效，请尽快使用。"+"</p>");
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendNormalEmail("验证码", true, sb.toString(), true, email);
            }
        }).start();
    }

    /**
     * 发送注册验证码
     * @param email 接收者的邮箱
     * @param code 验证码
     */
    public void sendModifyCode(final String email, String code) {
        final StringBuffer sb=new StringBuffer(); //实例化一个StringBuffer
        // font-weight: bold
        sb.append("<h4>"+"亲爱的"+email+"：</h4>").append("<p style='text-align: center; font-size: 16px; '>您好！您正在创琦杂谈网站上修改绑定的邮箱信息，验证码为: "+code
                +"。5分钟内有效，请尽快使用。"+"</p>");
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendNormalEmail("验证码", true, sb.toString(), true, email);
            }
        }).start();
    }

}
