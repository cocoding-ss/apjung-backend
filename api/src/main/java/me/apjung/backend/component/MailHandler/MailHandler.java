package me.apjung.backend.component.MailHandler;

import me.apjung.backend.property.AppProps.AppEnv;
import me.apjung.backend.property.AppProps.AppProps;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Component
public class MailHandler {
    private final JavaMailSender sender;
    private final AppProps appProps;

    public MailHandler(JavaMailSender sender, AppProps appProps) {
        this.sender = sender;
        this.appProps = appProps;
    }

    public void sendMail(CustomMailMessage message) throws MessagingException {
        this.authorizeEnvMessageTo(message.getTo());

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(message.getSubject());
        helper.setText(message.getText());
        helper.setFrom(message.getFrom());
        helper.setTo(message.getTo());

        sender.send(mimeMessage);
    }

    private void authorizeEnvMessageTo(String to) throws MailParseException {
        if (!AppEnv.PROD.equals(appProps.getCurrentEnv())) {
            if (Arrays.stream(appProps.getDevEmails().toArray()).noneMatch(to::equals)) {
                throw new MailParseException("개발 환경에서는 특정된 이메일에만 이메일으 전송할 수 있습니다");
            }
        }
    }
}
