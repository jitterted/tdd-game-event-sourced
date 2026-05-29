package dev.ted.tddgame;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Tag("springboot")
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class TddGameEventSourcedApplicationTests {

    @Test
    void contextLoads() {
    }

}
