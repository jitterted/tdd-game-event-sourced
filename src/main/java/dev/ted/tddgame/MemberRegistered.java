package dev.ted.tddgame;

public record MemberRegistered(Username username,
                               MemberId memberId) implements Event {
}
