package dev.ted.tddgame;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EventStoreTest {

    @Test
    void subscriberNotifiedWhenEventAppended() {
        EventStore eventStore = new InMemoryEventStore();
        AppliedEventsConsumer subscriber = new AppliedEventsConsumer();
        eventStore.subscribe(subscriber);

        eventStore.append(new GameCreated(null, "title", "gameHandle", "creator"));

        assertThat(subscriber.eventsApplied())
                .hasSize(1);
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
