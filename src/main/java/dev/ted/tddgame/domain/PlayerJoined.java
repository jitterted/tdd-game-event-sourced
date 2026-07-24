package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Event;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record PlayerJoined(MemberId memberId,
                           PlayerId playerId,
                           GameHandle gameHandle) implements Event {
}
