package dev.ted.tddgame.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MemberRegistrationControllerTest {

    @Test
    void getEndpointPrefillsFormWithMemberId() {
        MemberRegistrationController memberRegistrationController = new MemberRegistrationController();

        Model model = new ConcurrentModel();
        String viewName = memberRegistrationController.showRegistrationForm(model);

        assertThat(viewName)
                .isEqualTo("register-member");

        MemberRegistrationForm memberRegistrationForm = (MemberRegistrationForm)
                model.getAttribute("memberRegistrationForm");

        assertThat(memberRegistrationForm)
                .as("MemberRegistrationForm must be added to the model")
                .isNotNull();

        assertThat(memberRegistrationForm.memberIdString())
                .as("MemberId must have a non-null UUID String")
                .isNotNull();
    }

    @Test
    void postAcceptsPrincipalAndFormThenRedirectsToLobby() {
        MemberRegistrationController memberRegistrationController = new MemberRegistrationController();

        String redirectPage = memberRegistrationController.registerNewMember(
                () -> "principal-username",
                new MemberRegistrationForm(UUID.randomUUID().toString()));

        assertThat(redirectPage)
                .isEqualTo("redirect:/lobby");
    }
}