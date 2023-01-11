package me.apjung.backend.component.mailservice;

import me.apjung.backend.IntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

public class MailHandlerTest extends IntegrationTest {
    @Autowired
    private MailHandler mailHandler;

    @Test
    @Disabled
    public void mail_send() throws MessagingException {
        try {
            // given
            Context context = new Context();
            context.setVariable("title", "This is Test Title");
            context.setVariable("content", "This is <p> content");

            String text = mailHandler.getTemplateHtml("test", context);

            // when
            CustomMailMessage message = CustomMailMessage.builder()
                    .from("admin@apjung.me")
                    .to("labyu2020@gmail.com")
                    .subject("This is Test Email")
                    .text(text)
                    .isHtml(true)
                    .build();

            mailHandler.send(message);

            // then not exception
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
