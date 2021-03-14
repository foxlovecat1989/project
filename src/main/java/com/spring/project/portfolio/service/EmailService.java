package com.spring.project.portfolio.service;

import com.spring.project.portfolio.entities.Investor;
import com.spring.project.portfolio.mail.Emails;
import com.spring.project.portfolio.mail.SendEmail;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private ServletContext servletContext;
    
    @Async
    public void send(final Investor investor) {
        Runnable r = () -> {
            String sender = "台灣證卷模擬交易所";   // sender
            String to = investor.getEmail();        // to
            String title = "台灣證卷模擬交易所: Verification mail";  // title of email
            String url = "http://localhost:8080" + servletContext.getContextPath() + "/app/portfolio/verify/%d/%s";
            url = String.format(url, investor.getId(), investor.getCode());
            // Content
            String html = "<b>Welcome ~ !!! 台灣證卷模擬交易所</b></br>"
                    + "This is a verification mail,"
                    + "<p /><a href='" + url + "'><b>please check!!!</b></a>"
                    + "<p /> Please do not spam my email!";;

            SendEmail sendEmail = new SendEmail();
            try {
                sendEmail.submit(new Emails(sender, to, title, html));
            } catch (Exception e) {
                System.out.println("Email send error: " + e);
                e.printStackTrace();
            }
        };
        
        new Thread(r).start();
    }
}
