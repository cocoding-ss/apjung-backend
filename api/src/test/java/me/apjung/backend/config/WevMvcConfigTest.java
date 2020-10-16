package me.apjung.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WevMvcConfigTest {
    @Autowired
    MessageSource validationMessageSource;

    @Autowired
    MessageSource businessMessageSource;

    @Test
    public void YamlMessageSourceTest() {
        // given
        String code = "test.hello";

        // when
        String validationMessage = validationMessageSource.getMessage(code, null, Locale.KOREA);
        String businessMessage = businessMessageSource.getMessage(code, null, Locale.KOREA);

        System.out.println(validationMessage);
        System.out.println(businessMessage);

        // then
        assertEquals("validation", validationMessage);
        assertEquals("business", businessMessage);
    }
}
