package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.domain.GameHandle;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.PlayerJoined;
import dev.ted.tddgame.jeslib.Event;
import dev.ted.tddgame.jeslib.Result;

public class JoinGameCommand {

    private final EventStore eventStore;

    public JoinGameCommand(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public static JoinGameCommand createForTest() {
        return new JoinGameCommand(new InMemoryEventStore());
    }

    public Result<Event, String> execute(MemberId memberId, GameHandle gameHandle) {
        return Result.success(new PlayerJoined(memberId, null, null));
    }

}
