package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.config.TddGameConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@Tag("mvc")
@WebMvcTest(MemberRegistrationController.class)
@Import(TddGameConfiguration.class)
@WithMockUser
public class MemberRegistrationControllerMvcTest {

    @Autowired
    MockMvcTester mvc;

    @Test
    void getToRegisterShowsRegistrationPage() {
        mvc.get()
           .uri("/register")
           .assertThat()
           .hasStatus2xxSuccessful();
    }

    @Test
    void postToRegisterAcceptsInputAndRedirects() {
        mvc.post()
           .uri("/register")
           .param("memberIdString", UUID.randomUUID().toString())
           .with(csrf())
           .assertThat()
           .hasStatus3xxRedirection();
    }
}