package dev.ted.tddgame;

public record GameCreated(Long eventSequence, String title, String handle, String creator) implements Event {
}
