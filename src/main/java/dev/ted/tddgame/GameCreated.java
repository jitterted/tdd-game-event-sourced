package dev.ted.tddgame;

public record GameCreated(
        Long eventSequence,
        String gameHandle, String title,
        String creator
) implements Event {
}
