package me.apjung.backend.component.RandomStringBuilder;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class RandomStringBuilderTest {
    @Test
    public void generateAlphaNumericTest() {
        String test = RandomStringBuilder.generateAlphaNumeric(60);
        System.out.println(test);
    }

    @Test
    @Disabled
    @DisplayName("Alpha numeric 랜덤 생성기 성능 테스트")
    public void measureAlphaNumericGeneratorTest() {
        LocalDateTime start = LocalDateTime.now();
        final int length = 60;
        final long loopCount = 100_000;

        for (long i = 0; i < loopCount; i++) {
            RandomStringBuilder.generateAlphaNumeric(length);
        }

        System.out.println("It takes " + (Duration.between(start, LocalDateTime.now()).getNano() / 1_000_000) +  "ms.");
    }
}
