package com.malina.mail;

/**
 * Created by pawel on 14.01.18.
 */
public interface EmailService {

    public void sendMessage(String to, String subject, String text);


}
