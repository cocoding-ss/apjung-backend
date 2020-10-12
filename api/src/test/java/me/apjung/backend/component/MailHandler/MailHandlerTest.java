package me.apjung.backend.component.MailHandler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

@SpringBootTest
public class MailHandlerTest {
    @Autowired
    private MailHandler mailHandler;

    @Test
    public void 메일_전송() throws MessagingException {
        try {
            // given
            CustomMailMessage message = CustomMailMessage.builder()
                    .from("admin@apjung.me")
                    .to("labyu2020@gmail.com")
                    .subject("Text Subject")
                    .text("Test Message")
                    .isHtml(false)
                    .build();

            mailHandler.sendMail(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
