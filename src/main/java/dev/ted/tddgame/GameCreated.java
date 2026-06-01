package dev.ted.tddgame;

public record GameCreated(
        Long eventSequence,
        String title,
        String gameHandle,
        String creator
) implements Event {
}
