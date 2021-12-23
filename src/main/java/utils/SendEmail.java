package utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 邮箱发送工具类
 * */
public class SendEmail {
    /**
     * 发送邮件的方法
     * @param to : 发送对象
     * @param code: 激活码
     * */
    public static void sendMail(String to ,String code) throws  Exception
    {
        //创建连接对象，连接到服务器
        Properties props=new Properties();
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.qq.com");   // 发件人的邮箱的 SMTP 服务器地址，，我这里是用163邮箱发送的邮件//需要到自己发送邮箱的设置（在下面讲解）
        props.setProperty("mail.smtp.auth", "true");//请注意本地的邮箱服务器可以不用设置上面的三行
        Session session = Session.getInstance(props,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // TODO Auto-generated method stub
                return new PasswordAuthentication("2631223275@qq.com", "wyumnehifspgecja");
            }
        });
        //创建邮件对象
        MimeMessage msg = new MimeMessage(session);
        //发送一封激活邮件
        msg.setFrom(new InternetAddress("2631223275@qq.com"));
        //设置收件人
        msg.setRecipient(RecipientType.TO,new InternetAddress(to) );
        //设置邮件的主题
        msg.setSubject("This is your captcha.");
        //设置邮件的正文
        msg.setText("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");
        Transport.send(msg);
    }

    public static void main(String[] args) {
        try {
            SendEmail.sendMail("2631223275@qq.com","110");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}