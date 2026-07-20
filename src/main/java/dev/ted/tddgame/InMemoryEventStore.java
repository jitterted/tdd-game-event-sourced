package dev.ted.tddgame;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
                event.tags(),
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
        Predicate<StoredEvent> tagMatchPredicate =
                storedEvent -> storedEvent
                        .tags()
                        .containsAll(
                                queryPredicate
                                        .tags()
                                        .stream().map(Tag::asString)
                                        .collect(Collectors.toSet()));
        if (queryPredicate.tags().isEmpty()) {
            tagMatchPredicate = _ -> true;
        }
        return storedEvents.stream()
                           .filter(storedEvent -> queryPredicate
                                   .eventTypes()
                                   .contains(storedEvent.type()))
                           .filter(tagMatchPredicate)
                           .toList();
    }
}