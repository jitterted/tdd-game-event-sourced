package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.domain.EventFactory;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

class LobbyControllerTest {

    @Test
    void whenUserNotRegisteredAsMember_redirectsToRegistrationPage() {
        GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
        EventStore eventStore = new InMemoryEventStore();
        eventStore.append(EventFactory.memberRegistered("registered username"));
        LobbyController lobbyController = new LobbyController(projector, eventStore);

        Model model = new ConcurrentModel();
        String viewName = lobbyController.showLobby(() -> "unregistered username", model);

        assertThat(viewName)
                .isEqualTo("redirect:/register");
        assertThat(model.containsAttribute("availableGames"))
                .as("For unregistered member, we shouldn't be populating the model as we'll be redirecting anyway")
                .isFalse();
    }

    @Test
    void whenUserIsRegisteredAsMember_showLobbyPopulatesModelWithAvailableGames() {
        EventStore eventStore = new InMemoryEventStore();
        GamesAvailableToJoinProjector projector = new GamesAvailableToJoinProjector();
        eventStore.subscribe(projector);
        eventStore.append(EventFactory.gameCreatedWithTitle("First Game Title"));
        String registeredUsername = "registered username";
        eventStore.append(EventFactory.memberRegistered(registeredUsername));
        LobbyController lobbyController = new LobbyController(projector, eventStore);

        Model model = new ConcurrentModel();
        String viewName = lobbyController.showLobby(
                () -> registeredUsername,
                model);

        assertThat(viewName)
                .isEqualTo("lobby");

        assertThat(model.asMap())
                .extractingByKey("availableGames", InstanceOfAssertFactories.list(GamesAvailableToJoin.AvailableGame.class))
                .extracting(GamesAvailableToJoin.AvailableGame::title)
                .containsExactly("First Game Title");
    }
}