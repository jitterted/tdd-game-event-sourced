package dev.ted.tddgame;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

import java.time.Instant;
import java.util.UUID;

@NullMarked
public class EventFactory {
    public static GameCreated createEvent() {
        return gameCreatedWithTitle("title");
    }

    public static GameCreated gameCreatedWithTitle(String title) {
        return new GameCreated(null, "gameHandle", title, "creator");
    }

    public static @NonNull StoredEvent toStoredEvent(Event event) {
        return new StoredEvent(1L,
                               event.getClass(),
                               UUID.randomUUID(),
                               Instant.now(),
                               event,
                               null);
    }
}
