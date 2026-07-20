package dev.ted.tddgame.application;

import dev.ted.tddgame.domain.GameCreated;
import dev.ted.tddgame.domain.GameHandle;
import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Result;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LobbyTest {

    @Nested
    class CommandGeneratesEvent {

        @Test
        void createGame_GameCreated() {
            CreateGameCommand command = CreateGameCommand.createForTest();

            Result<Event, String> event = command.execute(
                    "Creator", "game-handle", "Title of Game");

            assertThat(event.isSuccess())
                    .as("executing CreateGameCommand should have Successful Result, but did not")
                    .isTrue();
            assertThat(event.value())
                    .isEqualTo(new GameCreated(
                            new GameHandle("game-handle"),
                            "Title of Game", "Creator"));
        }
    }

}