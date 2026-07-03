package dev.ted.tddgame;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryEventStore implements EventStore {
    private final List<StoredEvent> storedEvents = new ArrayList<>();
    private final List<EventConsumer> subscribers = new ArrayList<>();

    @Override
    public StoredEvent append(Event event) {
        // event sequence starts with 1, so +1 existing size
        long nextEventSequence = storedEvents.size() + 1;
        StoredEvent storedEvent = new StoredEvent(
                nextEventSequence,
                event.getClass(),
                UUID.randomUUID(),
                Instant.now(),
                event,
                null // commandId
        );

        storedEvents.add(storedEvent);

        subscribers.forEach(
                subscriber -> subscriber.apply(storedEvent)
        );

        return storedEvent;
    }

    @Override
    public List<StoredEvent> loadEvents() {
        return List.copyOf(storedEvents);
    }

    @Override
    public void subscribe(EventConsumer eventConsumer) {
        this.subscribers.add(eventConsumer);
    }

    @Override
    public List<StoredEvent> query(QueryPredicate queryPredicate) {
        Class<? extends Event> desiredEventType = queryPredicate.eventTypes().iterator().next();
        return storedEvents.stream()
                           .filter(storedEvent -> storedEvent.type().equals(desiredEventType))
                           .toList();
    }
}