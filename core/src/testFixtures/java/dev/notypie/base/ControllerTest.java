package dev.notypie.base;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Disabled
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yaml"})
public class ControllerTest {

}
