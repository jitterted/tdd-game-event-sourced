package dev.ted.tddgame;

public record GameCreated(String gameHandle, String title, String creator)
        implements Event {
}
