package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Tag;

import java.util.UUID;

public record PlayerId(UUID uuid) implements Tag {
    @Override
    public String asString() {
        return "player:" + uuid;
    }
}
