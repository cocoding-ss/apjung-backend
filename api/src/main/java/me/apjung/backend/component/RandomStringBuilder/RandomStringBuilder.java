package me.apjung.backend.component.RandomStringBuilder;

import java.util.SplittableRandom;

public class RandomStringBuilder {
    public static String  generateAlphaNumeric(int length) {
        int leftLimit = '0';
        int rightLimit = 'z';

        SplittableRandom random = new SplittableRandom();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || 65 <= i) && (i <= 90 || 97 <= i))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
