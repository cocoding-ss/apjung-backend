package me.apjung.backend.property;

import me.apjung.backend.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

public class SecurityPropsTest extends IntegrationTest {
    @Autowired
    SecurityProps securityProperty;

    @Test
    public void PropertyTest() {
        System.out.println("Security Properties :: ");
        System.out.println("authenticatedEndpoints :: " + securityProperty.getPermittedEndpoints());
    }
}
