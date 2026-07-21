package dev.ted.tddgame.adapter.in.web;

import dev.ted.tddgame.application.RegisterMemberCommand;
import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.application.port.QueryPredicate;
import dev.ted.tddgame.application.port.StoredEvent;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class MemberRegistrationControllerTest {

    @Test
    void getEndpointPrefillsFormWithMemberId() {
        MemberRegistrationController memberRegistrationController = new MemberRegistrationController(
                RegisterMemberCommand.createForTest()
        );

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
        EventStore eventStore = new InMemoryEventStore();
        MemberRegistrationController memberRegistrationController =
                new MemberRegistrationController(
                        new RegisterMemberCommand(eventStore));

        String memberUuidString = UUID.randomUUID().toString();
        String redirectPage = memberRegistrationController.registerNewMember(
                () -> "principal-username",
                new MemberRegistrationForm(memberUuidString));

        assertThat(redirectPage)
                .isEqualTo("redirect:/lobby");

        // TODO: talk to AssertJ folks, better way to do this to get a nicer field-by-field diff?
        assertThat(eventStore.query(new QueryPredicate(MemberRegistered.class)))
                .as("1 MemberRegistered event should have been appended to the Event Store")
                .extracting(StoredEvent::payload)
                .singleElement()
                .usingRecursiveComparison()
                .isEqualTo(new MemberRegistered(
                        Username.of("principal-username"),
                        MemberId.from(memberUuidString)));
    }

    @Test
    @Disabled("Until we start handling errors from the Register Member command execution")
    void usernameAlreadyRegisteredThenRegistrationFails_redirectToErrorPage() {
        EventStore eventStore = new InMemoryEventStore();
        MemberId existingMemberId = MemberId.createRandom();
        Username existingUsername = Username.of("existing-username");
        eventStore.append(new MemberRegistered(existingUsername, existingMemberId));
        MemberRegistrationController memberRegistrationController =
                new MemberRegistrationController(
                        new RegisterMemberCommand(eventStore));

        MemberRegistrationForm form = new MemberRegistrationForm(existingMemberId.uuid().toString());
        String redirectPage = memberRegistrationController.registerNewMember(
                () -> "existing-username", form);

        assertThat(redirectPage)
                .isEqualTo("redirect:/register/error");
    }
}