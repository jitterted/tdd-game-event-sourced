package dev.ted.tddgame;

import org.jspecify.annotations.NullMarked;

@NullMarked
public class EventFactory {
    public static GameCreated createEvent() {
        return createEventWithTitle("title");
    }

    public static GameCreated createEventWithTitle(String title) {
        return new GameCreated(null, "gameHandle", title, "creator");
    }
}
