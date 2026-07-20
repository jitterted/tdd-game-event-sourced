package dev.ted.tddgame;

import java.util.UUID;

public record MemberId(UUID uuid) implements Tag {
    @Override
    public String asString() {
        return "member:" + uuid;
    }
}
