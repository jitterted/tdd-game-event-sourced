package dev.ted.tddgame;

import java.util.List;

public class CreateGameCommand {

    private final EventStore eventStore;

    public CreateGameCommand(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public static CreateGameCommand createForTest() {
        return new CreateGameCommand(new InMemoryEventStore());
    }

    public Result<Event, String> execute(String creator, String gameHandle, String titleOfGame) {
        Event event = new GameCreated(new GameHandle(gameHandle), titleOfGame, creator);
        eventStore.append(event);
        return Result.success(event);
    }

    public List<StoredEvent> executionEvents() {
        return eventStore.loadEvents();
    }
}
