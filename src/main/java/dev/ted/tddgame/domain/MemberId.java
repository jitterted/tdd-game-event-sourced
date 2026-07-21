package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Tag;

import java.util.UUID;

public record MemberId(UUID uuid) implements Tag {

    public static MemberId createRandom() {
        return new MemberId(UUID.randomUUID());
    }

    public static MemberId from(String uuid) {
        return new MemberId(UUID.fromString(uuid));
    }

    @Override
    public String asString() {
        return "member:" + uuid;
    }
}
