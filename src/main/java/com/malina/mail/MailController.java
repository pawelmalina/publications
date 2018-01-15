package com.malina.mail;

import com.malina.auth.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by pawel on 14.01.18.
 */
@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = {"${origin.domain}","*"})
public class MailController {

    @Autowired
    public EmailService emailService;

    @Autowired
    private SessionState sessionState;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public HttpStatus sendMail(@RequestBody() MailDTO mail){
        if(!sessionState.isAuthenticated()){
            return HttpStatus.UNAUTHORIZED;
        }
        String text = mail.getText() + "\n\n" + sessionState.getCurrentUser().getFullName();
        emailService.sendMessage(mail.getTo(), mail.getSubject(), text);
        return HttpStatus.OK;
    }
}
