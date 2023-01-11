package me.apjung.backend.property;

import me.apjung.backend.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SecurityPropsTest extends IntegrationTest {
    @Autowired
    SecurityProps securityProperty;

    @Test
    void PropertyTest() {
        System.out.println("Security Properties :: ");
        System.out.println("authenticatedEndpoints :: " + securityProperty.getPermittedEndpoints());
    }
}
