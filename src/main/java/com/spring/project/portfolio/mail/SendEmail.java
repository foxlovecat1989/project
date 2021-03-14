package com.spring.project.portfolio.mail;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class SendEmail {
    
    public void submit(Emails emails) throws Exception {
        
        // Gamil & Authorization code
        final String gmailOfSender = "foxlovecat4work@gmail.com"; // Gmail address
        final String authPassword = "ofwrerwftzmsfjmw"; // Authorization code

        // smpt - setting information
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        // create session & communicate with 'stmp'
        Session session;
        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmailOfSender, authPassword);
            }
        });
        
        // create MimeMessage Object
        Message message = new MimeMessage(session);
        
        // From:
        InternetAddress internetAddress = new InternetAddress(gmailOfSender); // 你的 Gmail
        internetAddress.setPersonal(emails.getSender());
        message.setFrom(internetAddress);
        
        // To:
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(emails.getTo())
        );
        
        // Email 抬頭
        message.setSubject(emails.getTitle());
        
        // Text
        //message.setText("Dear Mail Crawler,\n\n Please do not spam my email!");
        // HTML
        message.setContent(emails.getHtml(), "text/html;charset=utf-8");

        // send email
        Transport.send(message);
        System.out.println("Sent Successfully!");
    }

}
