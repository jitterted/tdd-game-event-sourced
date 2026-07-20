package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Event;

public record MemberRegistered(Username username,
                               MemberId memberId) implements Event {
}
