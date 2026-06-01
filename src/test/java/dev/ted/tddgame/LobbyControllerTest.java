package dev.ted.tddgame;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

class LobbyControllerTest {

    @Test
    void showCreateGameFormPrepopulatedWithGameHandleAndEmptyTitle() {
        String gameHandle = "leaf-blower-20";
        LobbyController lobbyController = LobbyController.createForTest(gameHandle);

        Model model = new ConcurrentModel();
        String viewName = lobbyController.showCreateGameForm(model);

        assertThat(viewName)
                .isEqualTo("create-game");

        CreateGameForm createGameForm = (CreateGameForm)
                model.getAttribute("createGameForm");
        String emptyTitle = "";
        assertThat(createGameForm)
                .isEqualTo(new CreateGameForm(gameHandle, emptyTitle));
    }

    @Test
    void createGameRedirectsToJoinGamePage() {
        CreateGameCommand createGameCommand = CreateGameCommand.createForTest();
        LobbyController lobbyController = LobbyController.createForTest(createGameCommand);

        String redirectUrl = lobbyController.createGameCommand(
                new CreateGameForm("funny-ant-60", "The Olive Game 🫒"));

        assertThat(redirectUrl)
                .isEqualTo("redirect:/join-game");

        assertThat(createGameCommand.executionEvents())
                .as("Expected execution of CreateGameCommand to generate a GameCreated event")
                .containsExactly(
                        new GameCreated(
                                null,
                                "The Olive Game 🫒",
                                "funny-ant-60",
                                "UNKNOWN CREATOR"));
    }

}