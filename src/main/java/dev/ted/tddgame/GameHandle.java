package dev.ted.tddgame;

public record GameHandle(String gameHandle) implements Tag {
    @Override
    public String asString() {
        return "gamehandle:" + gameHandle;
    }
}