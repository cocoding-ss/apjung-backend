package me.apjung.backend.component.MailHandler;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailParseException;

import java.util.Date;
import java.util.Optional;

@Setter
@Getter
public class CustomMailMessage {
    private String to;
    private String from;
    private String subject;
    private String text;
    private boolean isHtml;

    @Builder
    public CustomMailMessage(String to, String from, String subject, String text, boolean isHtml) {
        this.to = to;
        this.from = Optional.ofNullable(from).orElseGet(() -> "admin@apjung.me");
        this.subject = subject;
        this.text = text;
        this.isHtml = isHtml;
    }
}
