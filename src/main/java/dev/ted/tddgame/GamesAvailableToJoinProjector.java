package dev.ted.tddgame;

public class GamesAvailableToJoinProjector implements EventConsumer {
    private final GamesAvailableToJoin gamesAvailableToJoin = new GamesAvailableToJoin();

    public GamesAvailableToJoin projection() {
        return gamesAvailableToJoin;
    }

    @Override
    public void apply(StoredEvent event) {
        if (event.payload() instanceof GameCreated gameCreated) {
            gamesAvailableToJoin.add(new GamesAvailableToJoin
                    .AvailableGame(gameCreated.gameHandle().gameHandle(),
                                   gameCreated.title()));
        }
    }
}
