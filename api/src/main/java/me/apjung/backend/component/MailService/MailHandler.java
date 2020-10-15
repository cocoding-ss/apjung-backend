package me.apjung.backend.component.MailService;

import me.apjung.backend.property.AppProps.AppEnv;
import me.apjung.backend.property.AppProps.AppProps;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Component
public class MailHandler {
    private final JavaMailSender sender;
    private final AppProps appProps;
    private final TemplateEngine emailTemplateEngine;

    public MailHandler(JavaMailSender sender, AppProps appProps, TemplateEngine emailTemplateEngine) {
        this.sender = sender;
        this.appProps = appProps;
        this.emailTemplateEngine = emailTemplateEngine;
    }

    public void send(CustomMailMessage message) {
        this.authorizeEnvMessageTo(message.getTo());

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), message.isHtml());
            helper.setFrom(message.getFrom());
            helper.setTo(message.getTo());

            sender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTemplateHtml(String templatePath, IContext context) {
        return emailTemplateEngine.process(templatePath, context);
    }

    private void authorizeEnvMessageTo(String to) throws MailParseException {
        if (!AppEnv.PROD.equals(appProps.getCurrentEnv())) {
            if (Arrays.stream(appProps.getDevEmails().toArray()).noneMatch(to::equals)) {
                throw new MailParseException("개발 환경에서는 특정된 이메일에만 이메일을 전송할 수 있습니다");
            }
        }
    }
}
