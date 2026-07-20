package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Tag;

public record Username(String username) implements Tag {
    @Override
    public String asString() {
        return "username:" + username;
    }
}
