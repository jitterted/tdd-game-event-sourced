package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.GameCreated;
import dev.ted.tddgame.jeslib.EventConsumer;

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
