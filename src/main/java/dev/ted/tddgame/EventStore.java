package dev.ted.tddgame;

import java.util.List;

public interface EventStore {
    void append(List<Event> events);

    void append(Event event);

    List<Event> loadEvents();
}
