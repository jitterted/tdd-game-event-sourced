package dev.ted.tddgame;

public record GameCreated(GameHandle gameHandle, String title, String creator)
        implements Event {
}
