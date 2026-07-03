package dev.ted.tddgame;

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
}