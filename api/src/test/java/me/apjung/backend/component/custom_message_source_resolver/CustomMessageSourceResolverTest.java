package me.apjung.backend.component.custom_message_source_resolver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomMessageSourceResolverTest {
    @Autowired
    CustomMessageSourceResolver customMessageSourceResolver;

    @Test
    public void getMessageTest() {
        // given
        String code = "test.hello";
        String codeArg = "test.args";
        List<String> args = new ArrayList<>();
        args.add("firstArgument");
        args.add("secondArgument");

        // when
        String validationMsg = customMessageSourceResolver.getValidationMessage(code);
        String validationArgsMsg = customMessageSourceResolver.getValidationMessage(codeArg, args.toArray());
        String businessMsg = customMessageSourceResolver.getBusinessMessage(code);
        String businessArgsMsg = customMessageSourceResolver.getBusinessMessage(codeArg, args.toArray());
        

        // then
        assertAll("CustomMessageSourceResolverTest-getMessageTest", () -> {
            assertEquals("validation", validationMsg);
            assertEquals("business", businessMsg);
            assertEquals("args" + args.get(0) + args.get(1), validationArgsMsg);
            assertEquals("args" + args.get(0) + args.get(1), businessArgsMsg);
        });
    }

}
