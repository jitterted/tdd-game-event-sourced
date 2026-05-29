package dev.ted.tddgame;

import java.util.List;

public class CreateGameCommand {

    public List<Event> execute(String creator, String gameHandle, String titleOfGame) {
        return List.of(new GameCreated(null, titleOfGame, gameHandle, creator));
    }
}
