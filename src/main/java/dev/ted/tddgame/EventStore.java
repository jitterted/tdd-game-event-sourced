package dev.ted.tddgame;

import java.util.List;

public interface EventStore {

    Event append(Event event);

    List<Event> loadEvents();

    void subscribe(EventConsumer eventConsumer);
}
