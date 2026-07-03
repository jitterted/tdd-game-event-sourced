package dev.ted.tddgame;

import java.time.Instant;
import java.util.UUID;

public record StoredEvent(
        long sequence,
        Class<? extends Event> type,
        UUID eventId,
        Instant timestamp,
        Event payload,
        UUID commandId
) {
}