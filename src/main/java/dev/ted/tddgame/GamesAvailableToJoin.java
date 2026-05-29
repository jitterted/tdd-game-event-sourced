package dev.ted.tddgame;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class GamesAvailableToJoin {
    private final List<String> gameTitles = new ArrayList<>();

    public void add(String titleOfGame) {
        gameTitles.add(titleOfGame);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GamesAvailableToJoin.class.getSimpleName() + "[", "]")
                .add("gameTitles=" + gameTitles)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GamesAvailableToJoin that = (GamesAvailableToJoin) o;
        return gameTitles.equals(that.gameTitles);
    }

    @Override
    public int hashCode() {
        return gameTitles.hashCode();
    }
}
