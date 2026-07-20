package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Event;

public record GameCreated(GameHandle gameHandle, String title, String creator)
        implements Event {
}
