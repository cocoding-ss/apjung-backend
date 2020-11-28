package me.apjung.backend.component.RandomStringBuilder;

import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomStringBuilder {
    private static final String[] NUMERIC = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private static final String[] ALPHA_UPPERCASE = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final String[] ALPHA_LOWERCASE = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] ALPHA_NUMERIC = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static final SplittableRandom random = new SplittableRandom();

    public static String generateAlphaNumeric(int length) {
        return IntStream.range(0, length)
                .mapToObj(i -> ALPHA_NUMERIC[random.nextInt(0, ALPHA_NUMERIC.length)])
                .collect(Collectors.joining());
    }
}
