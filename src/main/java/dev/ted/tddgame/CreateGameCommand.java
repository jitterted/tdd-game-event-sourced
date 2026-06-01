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

    public List<Event> execute(String creator, String gameHandle, String titleOfGame) {
        GameCreated event = new GameCreated(null, titleOfGame, gameHandle, creator);
        eventStore.append(event);
        return List.of(event);
    }

    public List<Event> executionEvents() {
        return eventStore.loadEvents();
    }
}
