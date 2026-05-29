package dev.ted.tddgame;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.*;

class LobbyControllerTest {

    @Test
    void createGameFormPrepopulatedWithGameHandle() {
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

}