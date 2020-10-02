package me.apjung.backend.property;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SecurityPropsTest {
    @Autowired
    SecurityProps securityProperty;

    @Test
    public void PropertyTest() {
        System.out.println("Security Properties :: ");
        System.out.println("authenticatedEndpoints :: " + securityProperty.getAuthenticatedEndpoints());
    }
}
