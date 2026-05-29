package dev.ted.tddgame;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

class LobbyControllerTest {

    @Test
    void showCreateGameFormPrepopulatedWithGameHandle() {
        LobbyController lobbyController = LobbyController.createForTest("leaf-blower-20");

        Model model = new ConcurrentModel();
        String viewName = lobbyController.showCreateGameForm(model);

        assertThat(viewName)
                .isEqualTo("create-game");

        CreateGameForm createGameForm = (CreateGameForm)
                model.getAttribute("createGameForm");
        assertThat(createGameForm)
                .isEqualTo(new CreateGameForm("leaf-blower-20"));
    }

    @Test
    void createGameRedirectsToJoinGamePage() {
        LobbyController lobbyController = LobbyController.createForTest();

        String redirectUrl = lobbyController.createGameCommand(new CreateGameForm("funny-ant-60"));

        assertThat(redirectUrl)
                .isEqualTo("redirect:/join-game");
    }

}