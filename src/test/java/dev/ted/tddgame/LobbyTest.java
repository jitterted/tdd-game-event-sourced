package dev.ted.tddgame;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class LobbyTest {

    @Nested
    class CommandGeneratesEvent {

        @Test
        void createGame_GameCreated() {
            CreateGameCommand command = CreateGameCommand.createForTest();

            List<Event> events = command.execute(
                    "Creator", "game-handle", "Title of Game");

            assertThat(events)
                    .containsExactly(new GameCreated(
                            null, "Title of Game", "game-handle", "Creator"));
        }
    }

    @Nested
    class EventUpdatesProjection {

        @Test
        void gameCreated_AddsGame_GamesAvailableToJoin() {
            GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
            Event gameCreated = new GameCreated(
                    1L, "Title of Game", "game-handle", "Creator");

            projector.apply(Stream.of(gameCreated));

            GamesAvailableToJoin expected = new GamesAvailableToJoin();
            expected.add("Title of Game");
            assertThat(projector.projection())
                    .isEqualTo(expected);
        }
    }
}