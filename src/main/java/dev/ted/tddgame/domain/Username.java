package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Tag;
import org.jspecify.annotations.NonNull;

public record Username(String username) implements Tag {
    public static @NonNull Username of(String username) {
        return new Username(username);
    }

    @Override
    public String asString() {
        return "username:" + username;
    }
}
