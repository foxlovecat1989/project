package com.spring.project.portfolio.mail;

public class Emails {
     private String sender;
     private String to;
     private String title;
     private String html;

    public Emails(String sender, String to, String title, String html) {
        this.sender = sender;
        this.to = to;
        this.title = title;
        this.html = html;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
     
}
