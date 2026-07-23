package dev.ted.tddgame.application;

import dev.ted.tddgame.application.port.EventStore;
import dev.ted.tddgame.application.port.InMemoryEventStore;
import dev.ted.tddgame.domain.EventFactory;
import dev.ted.tddgame.domain.MemberId;
import dev.ted.tddgame.domain.MemberRegistered;
import dev.ted.tddgame.domain.Username;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MembersTest {

    @Test
    void memberNotRegisteredIdForReturnsEmptyOptional() {
        EventStore eventStore = new InMemoryEventStore();
        Members members = new Members(eventStore);
        MemberRegistered memberRegistered =
                EventFactory.memberRegistered("registered member");
        eventStore.append(memberRegistered);

        Optional<MemberId> optionalMemberId = members.idFor(
                new Username("a non-registered member"));

        assertThat(optionalMemberId)
                .isNotPresent();
    }

    @Test
    void multipleMembersRegisteredWithSameUsernameThrowsException() {
        EventStore eventStore = new InMemoryEventStore();
        Members members = new Members(eventStore);
        String duplicatedRegistrationUsername = "duplicated registered member";
        eventStore.append(EventFactory.memberRegistered(duplicatedRegistrationUsername));
        eventStore.append(EventFactory.memberRegistered(duplicatedRegistrationUsername));

        assertThatExceptionOfType(DuplicateUsernamesRegistered.class)
                .as("Multiple MemberRegistered events were appended with the same username, which should not be allowed")
                .isThrownBy(() -> members.idFor(
                        new Username(duplicatedRegistrationUsername)))
                .withMessage("Username 'duplicated registered member' has 2 associated MemberRegistered events, which should not be allowed");
    }

    @Test
    void memberIdReturnedForRegisteredMember() {
        EventStore eventStore = new InMemoryEventStore();
        Members members = new Members(eventStore);
        String usernameString = "registered member";
        MemberRegistered memberRegistered = EventFactory.memberRegistered(usernameString);
        eventStore.append(memberRegistered);

        MemberId memberId = members.idFor(new Username(usernameString)).get();

        assertThat(memberId)
                .isEqualTo(memberRegistered.memberId());
    }
}