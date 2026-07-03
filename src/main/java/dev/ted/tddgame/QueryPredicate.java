package dev.ted.tddgame;

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
}
