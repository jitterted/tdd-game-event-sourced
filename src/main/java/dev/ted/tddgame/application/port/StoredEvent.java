package dev.ted.tddgame.application.port;

import dev.ted.tddgame.jeslib.Event;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record StoredEvent(
        long sequence,
        Class<? extends Event> type,
        UUID eventId,
        Instant timestamp,
        Set<String> tags,
        Event payload,
        UUID commandId
) {
}