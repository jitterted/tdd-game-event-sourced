package dev.ted.tddgame;

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