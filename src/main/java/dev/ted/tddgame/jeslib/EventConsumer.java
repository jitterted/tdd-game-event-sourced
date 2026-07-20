package dev.ted.tddgame.jeslib;

import dev.ted.tddgame.application.port.StoredEvent;

public interface EventConsumer {
    void apply(StoredEvent event);
}
