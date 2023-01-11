package me.apjung.backend;

import me.apjung.backend.config.jpa.JpaConfig;
import me.apjung.backend.config.QueryDslConfig;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDslConfig.class, JpaConfig.class})
@ImportAutoConfiguration({FlywayAutoConfiguration.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Disabled
public abstract class JpaTest {
    protected TestEntityManager testEntityManager;
}
