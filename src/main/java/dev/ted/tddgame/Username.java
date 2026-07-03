package dev.ted.tddgame;

public record Username(String username) implements Tag {
    @Override
    public String toTag() {
        return "username:" + username;
    }
}
