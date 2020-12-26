package me.apjung.backend.property;

import me.apjung.backend.IntegrationTest;
import me.apjung.backend.property.appprops.AppProps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class AppPropsTest extends IntegrationTest {
    @Autowired
    AppProps appProps;

    @Test
    public void appPropertyTest() {
        System.out.println("APP Properties ::");
        System.out.println("currentEnv :: " + appProps.getCurrentEnv());
        System.out.println("devEmails :: " + appProps.getDevEmails());
    }
}
