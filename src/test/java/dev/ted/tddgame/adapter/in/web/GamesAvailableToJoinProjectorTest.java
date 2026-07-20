package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.EventFactory;
import dev.ted.tddgame.domain.GameCreated;
import dev.ted.tddgame.domain.GameHandle;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GamesAvailableToJoinProjectorTest {

    @Test
    void projectorUpdatedWhenGameCreatedEventAppendedToEventStore() {
        EventStore eventStore = new InMemoryEventStore();
        GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
        eventStore.subscribe(projector);

        eventStore.append(new GameCreated(new GameHandle("tdd-game-90")
                , "title of game", "creator"));

        assertThat(projector.projection().games())
                .containsExactly(
                        new GamesAvailableToJoin.AvailableGame(
                                "tdd-game-90",
                                "title of game"));
    }

    @Nested
    class EventUpdatesProjection {

        @Test
        void gameCreated_AddsGame_GamesAvailableToJoin() {
            GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
            GameCreated gameCreated = new GameCreated(
                    new GameHandle("sun-dog-20"), "Title of Game", "Creator");

            StoredEvent storedEvent = EventFactory.toStoredEvent(gameCreated);
            projector.apply(storedEvent);

            GamesAvailableToJoin expected = new GamesAvailableToJoin();
            expected.add(new GamesAvailableToJoin.AvailableGame(
                    "sun-dog-20",
                    "Title of Game"));
            assertThat(projector.projection())
                    .isEqualTo(expected);
        }
    }

}