package dev.ted.tddgame;

import java.util.List;

public interface EventStore {

    StoredEvent append(Event event);

    List<StoredEvent> loadEvents();

    void subscribe(EventConsumer eventConsumer);

    /**
     * QueryPredicate is a combination of a Set<Class<Event>>
     * and Set<Tag> (where tag is basically a String found in the tags array in the event store)
     * e.g.:
     * QueryPredicate a = TicketSold.class, {cust:123-ABC, concert:989-ZZX}
     * QueryPredicate b = TicketAssigned.class, {cust:123-ABC}
     * QueryPredicate c= {TicketSold.class, TicketAssigned.class}, {cust:858-TOP}
     */
    List<StoredEvent> query(QueryPredicate queryPredicate);
}
