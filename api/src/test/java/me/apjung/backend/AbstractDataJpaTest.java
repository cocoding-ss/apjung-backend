package me.apjung.backend;

import lombok.RequiredArgsConstructor;
import me.apjung.backend.config.Jpa.JpaConfig;
import me.apjung.backend.config.QueryDslConfig;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({QueryDslConfig.class, JpaConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor
@Disabled
public abstract class AbstractDataJpaTest {
    protected TestEntityManager testEntityManager;
}
