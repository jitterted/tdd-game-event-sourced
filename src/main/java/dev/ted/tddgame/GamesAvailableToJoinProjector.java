package dev.ted.tddgame;

import java.util.stream.Stream;

public class GamesAvailableToJoinProjector {
    private final GamesAvailableToJoin gamesAvailableToJoin = new GamesAvailableToJoin();

    public void apply(Stream<Event> events) {
        events.forEach(event -> {
            if (event instanceof GameCreated gameCreated) {
                gamesAvailableToJoin.add(gameCreated.title());
            }
        });
    }

    public GamesAvailableToJoin projection() {
        return gamesAvailableToJoin;
    }
}
