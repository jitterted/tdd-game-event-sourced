package dev.ted.tddgame;

import java.util.List;

public interface EventStore {

    void append(Event event);

    List<Event> loadEvents();

    void subscribe(EventConsumer eventConsumer);
}
