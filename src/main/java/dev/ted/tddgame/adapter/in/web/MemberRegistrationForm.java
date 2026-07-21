package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.domain.MemberId;

public record MemberRegistrationForm(String memberIdString) {
    public MemberId memberId() {
        return MemberId.from(memberIdString);
    }
}
