package dev.ted.tddgame;

public interface EventConsumer {
    void apply(StoredEvent event);
}
