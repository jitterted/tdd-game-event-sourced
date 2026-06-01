package dev.ted.tddgame;

public class GamesAvailableToJoinProjector implements EventConsumer {
    private final GamesAvailableToJoin gamesAvailableToJoin = new GamesAvailableToJoin();

    public GamesAvailableToJoin projection() {
        return gamesAvailableToJoin;
    }

    @Override
    public void apply(Event event) {
        if (event instanceof GameCreated gameCreated) {
            gamesAvailableToJoin.add(gameCreated.title());
        }
    }
}
