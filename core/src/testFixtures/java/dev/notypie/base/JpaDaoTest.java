package dev.notypie.base;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
public class JpaDaoTest {
}
