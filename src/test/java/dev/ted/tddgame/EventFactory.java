package dev.ted.tddgame;

import org.jspecify.annotations.NullMarked;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@NullMarked
public class EventFactory {
    public static GameCreated gameCreated() {
        return gameCreatedWithTitle("title");
    }

    public static GameCreated gameCreatedWithTitle(String title) {
        return new GameCreated(new GameHandle("gameHandle"), title, "creator");
    }

    public static StoredEvent toStoredEvent(Event event) {
        return new StoredEvent(1L,
                               event.getClass(),
                               UUID.randomUUID(),
                               Instant.now(),
                               Set.of(), event,
                               null);
    }

    public static MemberRegistered memberRegistered(String username) {
        return new MemberRegistered(
                new Username(username),
                new MemberId(UUID.randomUUID()));
    }
}
