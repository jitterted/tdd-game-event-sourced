package dev.ted.tddgame.application.port;

import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Tag;

import java.util.Set;

public record QueryPredicate(Set<Class<? extends Event>> eventTypes,
                             Set<Tag> tags) {
    public QueryPredicate(Class<? extends Event> eventType, Tag tag) {
        this(Set.of(eventType), Set.of(tag));
    }

    public QueryPredicate(Class<? extends Event> eventType) {
        this(Set.of(eventType), Set.of());
    }

    public QueryPredicate(Set<Class<? extends Event>> eventTypes) {
        this(eventTypes, Set.of());
    }

    public QueryPredicate(Set<Class<? extends Event>> eventTypes, Tag tag) {
        this(eventTypes, Set.of(tag));
    }

    public QueryPredicate(Class<? extends Event> eventType, Set<Tag> tags) {
        this(Set.of(eventType), tags);
    }
}
