package me.apjung.backend.component.RandomStringBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomStringBuilderTest {
    @Test
    public void generateAlphaNumericTest() {
        String test = RandomStringBuilder.generateAlphaNumeric(60);
        System.out.println(test);
    }
}
