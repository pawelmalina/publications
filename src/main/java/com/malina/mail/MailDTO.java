package com.malina.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by pawel on 14.01.18.
 */
@Getter
@Setter
@NoArgsConstructor
public class MailDTO {

    private String to;
    private String subject;
    private String text;
}
