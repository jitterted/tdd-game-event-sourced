package dev.ted.tddgame.application;

public class DuplicateUsernamesRegistered extends RuntimeException {
    public DuplicateUsernamesRegistered(String message) {
        super(message);
    }
}
