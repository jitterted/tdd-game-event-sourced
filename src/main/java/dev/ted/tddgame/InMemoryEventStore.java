package dev.ted.tddgame;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStore implements EventStore {
    private final List<Event> events = new ArrayList<>();

    @Override
    public void append(List<Event> events) {
        this.events.addAll(events);
    }

    @Override
    public void append(Event event) {
        events.add(event);
    }

    @Override
    public List<Event> loadEvents() {
        return List.copyOf(events);
    }
}