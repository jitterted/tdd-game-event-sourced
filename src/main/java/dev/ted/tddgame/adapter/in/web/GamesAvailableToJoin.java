package dev.ted.tddgame.adapter.in.web;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class GamesAvailableToJoin {
    private final List<AvailableGame> availableGames = new ArrayList<>();

    public void add(AvailableGame availableGame) {
        availableGames.add(availableGame);
    }

    public List<AvailableGame> games() {
        return List.copyOf(availableGames);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GamesAvailableToJoin.class.getSimpleName() + "[", "]")
                .add("gameTitles=" + availableGames)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GamesAvailableToJoin that = (GamesAvailableToJoin) o;
        return availableGames.equals(that.availableGames);
    }

    @Override
    public int hashCode() {
        return availableGames.hashCode();
    }

    public record AvailableGame(String gameHandle, String title) {}

}
