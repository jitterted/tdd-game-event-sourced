package dev.ted.tddgame;

import org.springframework.boot.SpringApplication;

public class TestTddGameEventSourcedApplication {

    public static void main(String[] args) {
        SpringApplication.from(TddGameEventSourcedApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
