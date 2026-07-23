package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.QueryPredicate;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;

import java.util.List;

public class Members {
    public final EventStore eventStore;

    public Members(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public boolean isUnregisteredMember(Username username) {
        QueryPredicate memberForUsername = new QueryPredicate(MemberRegistered.class, username);
        List<StoredEvent> events = eventStore.query(memberForUsername);
        return events.isEmpty();
    }
}