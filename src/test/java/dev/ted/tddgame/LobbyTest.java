package dev.ted.tddgame;

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
                            null, "game-handle", "Title of Game", "Creator"));
        }
    }

    @Nested
    class EventUpdatesProjection {

        @Test
        void gameCreated_AddsGame_GamesAvailableToJoin() {
            GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
            GameCreated gameCreated = new GameCreated(
                    1L, "sun-dog-20", "Title of Game", "Creator");

            projector.apply(gameCreated);

            GamesAvailableToJoin expected = new GamesAvailableToJoin();
            expected.add(new GamesAvailableToJoin.AvailableGame(
                    "sun-dog-20",
                    "Title of Game"));
            assertThat(projector.projection())
                    .isEqualTo(expected);
        }
    }
}