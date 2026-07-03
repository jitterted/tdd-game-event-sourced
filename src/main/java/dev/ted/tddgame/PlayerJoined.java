package dev.ted.tddgame;

public record PlayerJoined(MemberId memberId,
                           PlayerId playerId,
                           String gameHandle) implements Event {
}
