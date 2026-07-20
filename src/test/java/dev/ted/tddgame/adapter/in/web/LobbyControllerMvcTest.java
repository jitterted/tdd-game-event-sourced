package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.config.TddGameConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Tag("mvc")
@WebMvcTest(LobbyController.class)
@Import(TddGameConfiguration.class)
@WithMockUser
public class LobbyControllerMvcTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void getToRootRedirectsToLobby() {
        mvc.get()
           .uri("/")
           .assertThat()
           .hasStatus3xxRedirection()
           .hasRedirectedUrl("/lobby");
    }

    @Test
    void getToLobbyEndpointReturnsOkWithLobbyViewName() {
        mvc.get()
           .uri("/lobby")
           .assertThat()
           .hasStatus2xxSuccessful()
           .hasViewName("lobby");
    }

    @Test
    void postToJoinEndpointReturnsRedirectToGameWithHandle() {
        mvc.post()
           .param("gameHandle", "book-store-80")
           .with(csrf())
           .uri("/join")
           .assertThat()
           .hasStatus3xxRedirection()
           .hasRedirectedUrl("/game"); // game handle is a Query param?
    }
}
