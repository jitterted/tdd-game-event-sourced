package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.QueryPredicate;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public class Members {
    public final EventStore eventStore;

    public Members(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public boolean isUnregisteredMember(Username username) {
        return memberRegisteredEventsFor(username).isEmpty();
    }

    public Optional<MemberId> idFor(Username username) {
        Optional<StoredEvent> memberRegisteredEvents = memberRegisteredEventsFor(username);
        return memberRegisteredEvents.stream()
                                     .findFirst()
                                     .map(StoredEvent::payload)
                                     .map(event -> ((MemberRegistered) event).memberId());
    }

    private Optional<StoredEvent> memberRegisteredEventsFor(Username username) {
        List<StoredEvent> foundEvents = eventStore.query(memberForUsernamePredicate(username));
        ensureOnlyZeroOrOneFoundEvents(username, foundEvents);
        return foundEvents.stream().findFirst();
    }

    private void ensureOnlyZeroOrOneFoundEvents(Username username, List<StoredEvent> foundEvents) {
        if (foundEvents.size() > 1) {
            throw new DuplicateUsernamesRegistered("Username '%s' has %s associated MemberRegistered events, which should not be allowed"
                                                           .formatted(username.username(),
                                                                      foundEvents.size()));
        }
    }

    private @NonNull QueryPredicate memberForUsernamePredicate(Username username) {
        return new QueryPredicate(MemberRegistered.class, username);
    }
}