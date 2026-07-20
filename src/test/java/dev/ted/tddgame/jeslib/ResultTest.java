package dev.ted.tddgame.jeslib;

import dev.ted.tddgame.domain.EventFactory;
import dev.ted.tddgame.domain.GameCreated;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ResultTest {

    @Test
    void successIsSuccessful() {
        GameCreated gameCreated = EventFactory.gameCreated();
        Result<Event, String> successfulEvent = Result.success(gameCreated);

        assertThat(successfulEvent.isSuccess())
                .isTrue();
        assertThat(successfulEvent.isFailure())
                .isFalse();
        assertThat(successfulEvent.value())
                .isEqualTo(gameCreated);
        assertThat(successfulEvent.failureInfo())
                .isNull();
    }

    @Test
    void failureIsNotSuccess() {
        Result<Event, String> failure = Result.failure("Oops, failed!");

        assertThat(failure.isSuccess())
                .isFalse();
        assertThat(failure.isFailure())
                .isTrue();
        assertThat(failure.value())
                .isNull();
        assertThat(failure.failureInfo())
                .isEqualTo("Oops, failed!");
    }

}