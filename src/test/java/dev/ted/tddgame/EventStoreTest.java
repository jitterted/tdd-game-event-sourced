package dev.ted.tddgame;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EventStoreTest {

    @Test
    void eventAssignedEventSequenceStartingWith1WhenAppendedAndReturned() {
        EventStore eventStore = new InMemoryEventStore();

        StoredEvent eventWithSequence = eventStore.append(EventFactory.gameCreated());

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

        eventStore.append(EventFactory.gameCreated());

        assertThat(subscriber.eventsApplied())
                .hasSize(1)
                .extracting(StoredEvent::sequence)
                .first()
                .isNotNull();
    }

    @Test
    void queryReturnsAllEventsMatchingSingleEventType() {
        EventStore eventStore = new InMemoryEventStore();
        eventStore.append(EventFactory.gameCreatedWithTitle("first"));
        eventStore.append(EventFactory.gameCreatedWithTitle("second"));
        eventStore.append(EventFactory.memberRegistered("blue"));

        List<StoredEvent> events = eventStore.query(
                new QueryPredicate(GameCreated.class));

        assertThat(events)
                .hasSize(2);
    }

    @Test
    void queryReturnsAllEventsMatchingMultipleEventTypes() {

    }

    @Test
    @Disabled("until query for just matching event type works")
    void queryReturnsEventsMatchingSingleEventTypeAndSpecificTag() {
        EventStore eventStore = new InMemoryEventStore();
        eventStore.append(EventFactory.memberRegistered("my_username"));
        MemberRegistered blueMemberRegistered = EventFactory.memberRegistered("blue");
        eventStore.append(blueMemberRegistered);

        QueryPredicate memberQueryPredicate =
                new QueryPredicate(MemberRegistered.class,
                                   new Username("blue"));
        List<StoredEvent> events = eventStore.query(memberQueryPredicate);

        assertThat(events)
                .extracting(StoredEvent::payload)
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
