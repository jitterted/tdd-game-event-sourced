package dev.ted.tddgame.jeslib;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.application.port.QueryPredicate;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.EventFactory;
import dev.ted.tddgame.domain.GameCreated;
import dev.ted.tddgame.domain.GameHandle;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.PlayerJoined;
import dev.ted.tddgame.domain.Username;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
                .hasSize(2)
                .extracting(StoredEvent::payload)
                .hasOnlyElementsOfType(GameCreated.class);
    }

    @Test
    void queryReturnsAllEventsMatchingMultipleEventTypes() {
        EventStore eventStore = new InMemoryEventStore();
        eventStore.append(EventFactory.gameCreatedWithTitle("first"));
        eventStore.append(EventFactory.gameCreatedWithTitle("second"));
        eventStore.append(EventFactory.playerJoined());
        eventStore.append(EventFactory.memberRegistered("blue"));

        List<StoredEvent> events = eventStore.query(
                new QueryPredicate(Set.of(GameCreated.class,
                                          PlayerJoined.class)));

        assertThat(events)
                .hasSize(3)
                .extracting(StoredEvent::payload)
                .hasOnlyElementsOfTypes(GameCreated.class, PlayerJoined.class);
    }

    @Test
    void queryReturnsEventsMatchingSingleEventTypeAndSingleTag() {
        EventStore eventStore = new InMemoryEventStore();
        eventStore.append(EventFactory.gameCreatedWithTitle("first"));
        eventStore.append(EventFactory.memberRegistered("my_username"));
        MemberRegistered blueMemberRegistered =
                EventFactory.memberRegistered("blue");
        eventStore.append(blueMemberRegistered);

        QueryPredicate memberQueryPredicate =
                new QueryPredicate(MemberRegistered.class,
                                   new Username("blue"));
        List<StoredEvent> events = eventStore.query(memberQueryPredicate);

        assertThat(events)
                .extracting(StoredEvent::payload)
                .containsExactly(blueMemberRegistered);
    }

    @Test
    void eventsMatchingMultipleTypesAndSingleTag() {
        EventStore eventStore = new InMemoryEventStore();
        String gameHandle = "single-game-handle";
        GameCreated gameCreated = EventFactory.gameCreatedWithHandle(gameHandle);
        eventStore.append(gameCreated);
        eventStore.append(EventFactory.gameCreated());
        eventStore.append(EventFactory.memberRegistered());
        PlayerJoined playerJoined = EventFactory.playerJoined(gameHandle);
        eventStore.append(playerJoined);

        QueryPredicate queryPredicate =
                new QueryPredicate(Set.of(GameCreated.class, PlayerJoined.class),
                                   new GameHandle(gameHandle));
        List<StoredEvent> events = eventStore.query(queryPredicate);

        assertThat(events)
                .extracting(StoredEvent::payload)
                .containsExactlyInAnyOrder(gameCreated, playerJoined);
    }

    @Test
    void eventsMatchingSingleEventTypeAndMultipleTags() {
        EventStore eventStore = new InMemoryEventStore();
        String gameHandle = "game-handle-to-join";
        eventStore.append(EventFactory.gameCreatedWithHandle(gameHandle));
        PlayerJoined findThisPlayerJoined = EventFactory.playerJoined(gameHandle);
        eventStore.append(findThisPlayerJoined);
        PlayerJoined doNotFindThisPlayerJoined = EventFactory.playerJoined(gameHandle);
        eventStore.append(doNotFindThisPlayerJoined);

        QueryPredicate queryPredicate =
                new QueryPredicate(PlayerJoined.class,
                                   Set.of(
                                   findThisPlayerJoined.gameHandle(),
                                   findThisPlayerJoined.memberId()));
        List<StoredEvent> events = eventStore.query(queryPredicate);

        assertThat(events)
                .extracting(StoredEvent::payload)
                .containsExactly(findThisPlayerJoined);
    }

    // Needs multiple different event types that share 2 (or more) Tag types
    // e.g., PlayerJoined and PlayerDiscardedCard (or some test-only event)
    void eventsMatchingMultipleEventTypesAndMultipleTags() {

    }

    @Nested
    class TaggingStoredEvents {
        @Test
        void appendAddsTagPropertiesToTagsMetadata() {
            EventStore eventStore = new InMemoryEventStore();

            GameCreated gameCreated = EventFactory.gameCreatedWithTitle("title");
            StoredEvent storedEvent = eventStore.append(gameCreated);

            assertThat(storedEvent.tags())
                    .containsExactly(gameCreated.gameHandle().asString());
        }

        @Test
        void allEventComponentsThatAreTagsConvertedToStrings() throws InvocationTargetException, IllegalAccessException {
            UUID uuid = UUID.randomUUID();
            Event event = new MemberRegistered(
                    new Username("member_username"),
                    new MemberId(uuid));

            Set<String> tagStrings = event.tags();

            assertThat(tagStrings)
                    .containsExactlyInAnyOrder(
                            "username:member_username",
                            "member:" + uuid);
        }

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
