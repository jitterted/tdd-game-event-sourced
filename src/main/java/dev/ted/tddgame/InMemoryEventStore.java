package dev.ted.tddgame;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStore implements EventStore {
    private final List<Event> events = new ArrayList<>();
    private final List<EventConsumer> subscribers = new ArrayList<>();

    @Override
    public void append(Event event) {
        events.add(event);
        subscribers.forEach(
                subscriber -> subscriber.apply(event)
        );
    }

    @Override
    public List<Event> loadEvents() {
        return List.copyOf(events);
    }

    @Override
    public void subscribe(EventConsumer eventConsumer) {
        this.subscribers.add(eventConsumer);
    }
}