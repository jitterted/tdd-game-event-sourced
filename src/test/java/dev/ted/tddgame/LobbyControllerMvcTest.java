package dev.ted.tddgame;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Tag("mvc")
@WebMvcTest(LobbyController.class)
@Import(TddGameConfiguration.class)
@WithMockUser
public class LobbyControllerMvcTest {

    @Autowired
    GamesAvailableToJoinProjector gamesAvailableToJoinProjector;

    @Autowired
    MockMvcTester mvc;

    @Test
    void getToCreateGameEndpointReturnsOk() {
        mvc.get()
           .uri("/create-game")
           .assertThat()
           .hasStatus2xxSuccessful();
    }

    @Test
    void postToCreateGameEndpointReturnsRedirect() {
        mvc.post()
           .param("gameHandle", "book-store-80")
           .param("title", "Posted Title")
           .with(csrf())
           .uri("/create-game")
           .assertThat()
           .hasStatus3xxRedirection();

        assertThat(gamesAvailableToJoinProjector.projection().games())
                .containsExactly(new GamesAvailableToJoin
                        .AvailableGame("book-store-80", "Posted Title"));
    }
}
