package dev.ted.tddgame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EventStoreTest {

    @Test
    void eventAssignedEventSequenceStartingWith1WhenAppendedAndReturned() {
        EventStore eventStore = new InMemoryEventStore();

        Event eventWithSequence = eventStore.append(EventFactory.createEvent());

        assertThat(eventWithSequence.eventSequence())
                .isEqualTo(1L);
    }

    @Test
    void twoEventsAppendedGetSequenceNumbersInOrderOfAppending() {
        EventStore eventStore = new InMemoryEventStore();
        Event firstEvent = EventFactory.createEventWithTitle("first");
        Event secondEvent = EventFactory.createEventWithTitle("second");
        List<GameCreated> eventsWithSequence = new ArrayList<>();

        eventsWithSequence.add((GameCreated) eventStore.append(firstEvent));
        eventsWithSequence.add((GameCreated) eventStore.append(secondEvent));

        assertThat(eventsWithSequence)
                .extracting(Event::eventSequence, GameCreated::title)
                .containsExactly(tuple(1L, "first"),
                                 tuple(2L, "second"));
    }

    @Test
    void subscriberNotifiedWhenEventAppended() {
        EventStore eventStore = new InMemoryEventStore();
        AppliedEventsConsumer subscriber = new AppliedEventsConsumer();
        eventStore.subscribe(subscriber);

        eventStore.append(EventFactory.createEvent());

        assertThat(subscriber.eventsApplied())
                .hasSize(1)
                .extracting(Event::eventSequence)
                .first()
                .isNotNull();
    }

}

class AppliedEventsConsumer implements EventConsumer {
    private final List<Event> eventsApplied = new ArrayList<>();

    public List<Event> eventsApplied() {
        return eventsApplied;
    }

    @Override
    public void apply(Event event) {
        eventsApplied.add(event);
    }
}
