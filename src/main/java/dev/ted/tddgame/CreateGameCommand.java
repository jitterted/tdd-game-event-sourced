package dev.ted.tddgame;

import java.util.Collections;
import java.util.List;

public class CreateGameCommand {

    private List<Event> events = Collections.emptyList();

    public List<Event> execute(String creator, String gameHandle, String titleOfGame) {
        events = List.of(new GameCreated(null, titleOfGame, gameHandle, creator));
        return events;
    }

    public List<Event> executionEvents() {
        return List.copyOf(events);
    }
}
