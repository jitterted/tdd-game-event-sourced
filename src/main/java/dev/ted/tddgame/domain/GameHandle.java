package dev.ted.tddgame.domain;

import dev.ted.tddgame.jeslib.Tag;

public record GameHandle(String gameHandle) implements Tag {
    @Override
    public String asString() {
        return "gamehandle:" + gameHandle;
    }
}