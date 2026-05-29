package dev.ted.tddgame;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@Tag("mvc")
@WebMvcTest(LobbyController.class)
@Import(TddGameConfiguration.class)
public class LobbyControllerMvcTest {

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
           .param("handle", "book-store-80")
           .uri("/create-game")
           .assertThat()
           .hasStatus3xxRedirection();
    }
}
