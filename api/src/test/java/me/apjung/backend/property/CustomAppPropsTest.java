package me.apjung.backend.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomAppPropsTest {
    @Autowired
    AppProps appProps;

    @Test
    public void appPropertyTest() {
        System.out.println("APP Properties ::");
        System.out.println("currentEnv :: " + appProps.getCurrentEnv());
    }
}
