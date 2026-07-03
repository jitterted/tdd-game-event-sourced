package dev.ted.tddgame;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.assertj.core.api.Assertions.*;

class LobbyControllerTest {

    private static final Principal DUMMY_PRINCIPAL = () -> "dummy username";

    @Test
    void showLobbyPopulatesModelWithAvailableGames() {
        GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
        GameCreated gameCreated = EventFactory.gameCreatedWithTitle("First Game Title");
        projector.apply(EventFactory.toStoredEvent(gameCreated));
        LobbyController lobbyController = new LobbyController(projector);

        Model model = new ConcurrentModel();
        lobbyController.showLobby(DUMMY_PRINCIPAL, model);

        assertThat(model.asMap())
                .extractingByKey("availableGames", InstanceOfAssertFactories.list(GamesAvailableToJoin.AvailableGame.class))
                .extracting(GamesAvailableToJoin.AvailableGame::title)
                .containsExactly("First Game Title");
    }
}