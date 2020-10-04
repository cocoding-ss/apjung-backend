package me.apjung.backend.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtPropsTest {
    @Autowired
    JwtProps jwtProps;

    @Test
    public void jwtPropertyTest() {
        System.out.println("JWT PROPS ::");
        System.out.println("secret :: " + jwtProps.getSecret());
        System.out.println("expirationTime :: " + jwtProps.getExpirationTimeSec());
    }
}
