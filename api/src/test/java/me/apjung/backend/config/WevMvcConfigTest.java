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
    MessageSource messageSource;

    @Test
    public void YamlMessageSourceTest() {
        // given
        String code = "test.hello";

        // when
        String message = messageSource.getMessage(code, null, Locale.KOREA);

        // then
        assertEquals("world", message);
    }
}
