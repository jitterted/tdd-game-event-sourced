package dev.ted.tddgame;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class EventStoreTest {

    @Test
    void eventAssignedEventSequenceStartingWith1WhenAppendedAndReturned() {
        EventStore eventStore = new InMemoryEventStore();

        StoredEvent eventWithSequence = eventStore.append(EventFactory.createEvent());

        assertThat(eventWithSequence.sequence())
                .isEqualTo(1L);
    }

    @Test
    void twoEventsAppendedGetSequenceNumbersInOrderOfAppending() {
        EventStore eventStore = new InMemoryEventStore();
        Event firstEvent = EventFactory.gameCreatedWithTitle("first");
        Event secondEvent = EventFactory.gameCreatedWithTitle("second");
        List<StoredEvent> eventsWithSequence = new ArrayList<>();

        eventsWithSequence.add(eventStore.append(firstEvent));
        eventsWithSequence.add(eventStore.append(secondEvent));

        assertThat(eventsWithSequence)
                .extracting(StoredEvent::sequence)
                .containsExactly(1L, 2L);
        assertThat(eventsWithSequence)
                .map(StoredEvent::payload)
                .extracting(event -> ((GameCreated) event).title())
                .containsExactly("first", "second");
    }

    @Test
    void subscriberNotifiedWhenEventAppended() {
        EventStore eventStore = new InMemoryEventStore();
        AppliedEventsConsumer subscriber = new AppliedEventsConsumer();
        eventStore.subscribe(subscriber);

        eventStore.append(EventFactory.createEvent());

        assertThat(subscriber.eventsApplied())
                .hasSize(1)
                .extracting(StoredEvent::sequence)
                .first()
                .isNotNull();
    }

    @Test
    @Disabled("until we finish migrating to StoredEvent")
    void queryReturnsMatchingEvents() {
        EventStore eventStore = new InMemoryEventStore();
        eventStore.append(new MemberRegistered(
                new Username("my_username"),
                new MemberId(UUID.randomUUID())));
        MemberRegistered blueMemberRegistered = new MemberRegistered(
                new Username("blue"),
                new MemberId(UUID.randomUUID()));
        eventStore.append(blueMemberRegistered);

        QueryPredicate memberQueryPredicate =
                new QueryPredicate(MemberRegistered.class,
                                   new Username("blue"));
        List<Event> events = eventStore.query(memberQueryPredicate);

        assertThat(events)
                .containsExactly(blueMemberRegistered);
    }
}

class AppliedEventsConsumer implements EventConsumer {
    private final List<StoredEvent> eventsApplied = new ArrayList<>();

    public List<StoredEvent> eventsApplied() {
        return eventsApplied;
    }

    @Override
    public void apply(StoredEvent event) {
        eventsApplied.add(event);
    }
}
