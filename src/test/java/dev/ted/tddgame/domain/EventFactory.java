package dev.ted.tddgame.domain;

import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.jeslib.Event;
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
        return new GameCreated(new GameHandle("gameHandle"),
                               title,
                               "irrelevant-creator");
    }

    public static GameCreated gameCreatedWithHandle(String gameHandle) {
        return gameCreatedWithHandle(new GameHandle(gameHandle));
    }

    public static GameCreated gameCreatedWithHandle(GameHandle gameHandle) {
        return new GameCreated(gameHandle,
                               "irrelevant-title",
                               "irrelevant-creator");
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

    public static PlayerJoined playerJoined() {
        return playerJoined("irrelevant-game-handle");
    }

    public static PlayerJoined playerJoined(String gameHandle) {
        return new PlayerJoined(new MemberId(UUID.randomUUID()),
                                new PlayerId(UUID.randomUUID()),
                                new GameHandle(gameHandle));
    }

    public static MemberRegistered memberRegistered() {
        return memberRegistered("irrelevant-username");
    }
}
