package dev.ted.tddgame;

public record PlayerJoined(MemberId memberId,
                           PlayerId playerId,
                           GameHandle gameHandle) implements Event {
}
