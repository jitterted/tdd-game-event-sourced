package dev.ted.tddgame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GamesAvailableToJoinProjectorTest {

    @Test
    void projectorUpdatedWhenGameCreatedEventAppendedToEventStore() {
        EventStore eventStore = new InMemoryEventStore();
        GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
        eventStore.subscribe(projector);

        eventStore.append(new GameCreated(null, "title of game", "gameHandle", "creator"));

        assertThat(projector.projection().gameTitles())
                .containsExactly("title of game");
    }
}