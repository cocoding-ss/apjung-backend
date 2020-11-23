package me.apjung.backend.component.RandomStringBuilder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RandomStringBuilderTest {
    @Test
    public void generateAlphaNumericTest() {
        String test = Optional.ofNullable(RandomStringBuilder.generateAlphaNumeric(60)).orElseThrow();
        System.out.println(test);
    }
}
