package dev.ted.tddgame;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEventStore implements EventStore {
    private final List<Event> events = new ArrayList<>();
    private final List<EventConsumer> subscribers = new ArrayList<>();

    @Override
    public Event append(Event event) {
        events.add(event);
        long nextEventSequence = events.size();

        GameCreated gameCreated = (GameCreated) event;
        GameCreated eventWithSequence = new GameCreated(
                nextEventSequence, gameCreated.title(),
                gameCreated.gameHandle(), gameCreated.creator());

        subscribers.forEach(
                subscriber -> subscriber.apply(eventWithSequence)
        );

        return eventWithSequence;
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