package dev.ted.tddgame;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.assertj.core.api.Assertions.*;

class CreateGameControllerTest {

    @Test
    void showCreateGameFormPrepopulatedWithGameHandleAndEmptyTitle() {
        String gameHandle = "leaf-blower-20";
        CreateGameController createGameController = CreateGameController.createForTest(gameHandle);

        Model model = new ConcurrentModel();
        String viewName = createGameController.showCreateGameForm(model);

        assertThat(viewName)
                .isEqualTo("create-game");

        CreateGameForm createGameForm = (CreateGameForm)
                model.getAttribute("createGameForm");
        String emptyTitle = "";
        assertThat(createGameForm)
                .isEqualTo(new CreateGameForm(gameHandle, emptyTitle));
    }

    @Test
    void createGameCommandExecutesCommandAndRedirectsToJoinGamePage() {
        CreateGameCommand createGameCommand = CreateGameCommand.createForTest();
        CreateGameController createGameController = CreateGameController.createForTest(createGameCommand);

        CreateGameForm createGameForm = new CreateGameForm("funny-ant-60", "The Olive Game 🫒");
        Principal principal = () -> "principal_name"; // Principal.getName() = authName
        createGameController.createGameCommand(principal, createGameForm);

        assertThat(createGameCommand.executionEvents())
                .as("Expected execution of CreateGameCommand to generate a GameCreated event")
                .extracting(StoredEvent::payload)
                .containsExactly(
                        new GameCreated(
                                new GameHandle("funny-ant-60"), "The Olive Game 🫒",
                                "principal_name"));
    }

}