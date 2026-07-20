package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Event;

public record PlayerJoined(MemberId memberId,
                           PlayerId playerId,
                           GameHandle gameHandle) implements Event {
}
