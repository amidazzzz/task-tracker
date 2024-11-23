package org.amida.email_sender.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email {
    private String recipient;
    private String subject;
    private String body;
}
