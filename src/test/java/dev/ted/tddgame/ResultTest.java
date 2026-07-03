package dev.ted.tddgame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ResultTest {

    @Test
    void successIsSuccessful() {
        Result<Event, String> successfulEvent = Result.success(
                new GameCreated("irrelevant", "irrelevant", "irrelevant"));

        assertThat(successfulEvent.isSuccess())
                .isTrue();
        assertThat(successfulEvent.isFailure())
                .isFalse();
        assertThat(successfulEvent.value())
                .isEqualTo(new GameCreated("irrelevant", "irrelevant", "irrelevant"));
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